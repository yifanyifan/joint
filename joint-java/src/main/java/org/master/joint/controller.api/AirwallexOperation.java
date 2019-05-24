package org.master.joint.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.master.joint.bean.BeanUtils;
import org.master.joint.bean.DataGrid;
import org.master.joint.bean.Version;
import org.master.joint.dto.airwallex.AccountRedis;
import org.master.joint.dto.airwallex.request.Account_details;
import org.master.joint.dto.airwallex.request.Address;
import org.master.joint.dto.airwallex.request.AirWallexAccountsCreate;
import org.master.joint.dto.airwallex.request.Attachments1;
import org.master.joint.dto.airwallex.request.Attachments2;
import org.master.joint.dto.airwallex.request.Business_address;
import org.master.joint.dto.airwallex.request.Business_details;
import org.master.joint.dto.airwallex.request.ChargesCreateRequest;
import org.master.joint.dto.airwallex.request.Identity_files;
import org.master.joint.dto.airwallex.request.Legal_rep_details;
import org.master.joint.dto.airwallex.request.Primary_contact;
import org.master.joint.enums.IdentityFilesTagEnum;
import org.master.joint.enums.IndustryCategoryEnum;
import org.master.joint.enums.PurposeEnum;
import org.master.joint.http.JsoupUtils;
import org.master.joint.service.DemoService;
import org.master.joint.service.RedisHashService;
import org.master.joint.vo.airwallex.AirWallexRequestVO;
import org.master.joint.vo.airwallex.AirWallexResponseVO;
import org.master.joint.vo.airwallex.BalancesCurrentRequestVO;
import org.master.joint.vo.airwallex.BalancesCurrentResponse;
import org.master.joint.vo.airwallex.BalancesHistoryRequestVO;
import org.master.joint.vo.airwallex.BalancesHistoryResponse;
import org.master.joint.vo.airwallex.ChargesCreateRequestVO;
import org.master.joint.vo.airwallex.ChargesCreateResponseVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Yifan
 * @Description: Airwallex
 * @date: 2019/5/6
 */
@RequestMapping("/api" + Version.VERSION)
@RestController
@Slf4j
@Api(value = "AirwallexOperation", tags = "Airwallex操作类")
public class AirwallexOperation {
    private static final String AIRWALLEXACCOUNT = "AirwallexAccount";

    @Value("${airwallex.authenticationUrl}")
    private String authenticationUrl;

    @Value("${airwallex.filesUploadUrl}")
    private String filesUploadUrl;

    @Value("${airwallex.accountsCreateUrl}")
    private String accountsCreateUrl;

    @Value("${airwallex.balancesCurrentUrl}")
    private String balancesCurrentUrl;

    @Value("${airwallex.balancesHistoryUrl}")
    private String balancesHistoryUrl;

    @Value("${airwallex.chargesCreateUrl}")
    private String chargesCreateUrl;

    @Reference
    private RedisHashService redisHashService;

    @Reference
    private DemoService demoService;

    @ApiOperation(value = "子账户划拨到主账户", notes = "by yifan")
    @RequestMapping(value = "/chargesCreate", method = RequestMethod.POST)
    public DataGrid chargesCreate(ChargesCreateRequestVO chargesCreateRequestVO) {
        DataGrid dataGrid = new DataGrid();

        try {
            AirWallexRequestVO airWallexRequestVO = new AirWallexRequestVO();
            BeanUtils.copyExclude(chargesCreateRequestVO, airWallexRequestVO);

            //授权
            AirWallexResponseVO airWallexAuthenticationResponse = this.authentication(airWallexRequestVO);
            if (StringUtils.isNotEmpty(airWallexAuthenticationResponse.getMessage())) {
                dataGrid.setMsg("授权失败：" + airWallexAuthenticationResponse.getMessage());
                return dataGrid;
            }
            String token = airWallexAuthenticationResponse.getToken();

            Map header = getHeaderAuthorRequest(token);

            AccountRedis accountRedis = redisHashService.get(AIRWALLEXACCOUNT, chargesCreateRequestVO.getEmail(), AccountRedis.class);
            if (accountRedis == null) {
                dataGrid.setMsg("Redis中未找到子账户Email对应的ID");
                return dataGrid;
            }

            ChargesCreateRequest airWallexChargesCreate = new ChargesCreateRequest();
            BeanUtils.copyExclude(chargesCreateRequestVO, airWallexChargesCreate);
            airWallexChargesCreate.setReason(chargesCreateRequestVO.getReason().getSpec());
            airWallexChargesCreate.setRequest_id(UUID.randomUUID().toString());
            airWallexChargesCreate.setSource(accountRedis.getId());

            log.info("请求子账户划拨到主账户接口开始，header={}, body={}", header.toString(), JSON.toJSONString(airWallexChargesCreate));
            String responseStr = JsoupUtils.post(chargesCreateUrl, header, JSON.toJSONString(airWallexChargesCreate), null);
            log.info("请求子账户划拨到主账户接口结束, responseObj={}", responseStr);

            ChargesCreateResponseVO chargesCreateResponseVO = JSONObject.parseObject(responseStr, ChargesCreateResponseVO.class);

            if (StringUtils.isEmpty(chargesCreateResponseVO.getMessage())) {
                dataGrid.setObj(chargesCreateResponseVO);
                dataGrid.setFlag(true);
                dataGrid.setMsg("子账户划拨到主账户成功");
            } else {
                dataGrid.setMsg("子账户划拨到主账户失败：" + chargesCreateResponseVO.getMessage());
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            dataGrid.setMsg("子账户划拨到主账户异常：" + e.getMessage());
        }
        return dataGrid;
    }

    @ApiOperation(value = "查询子账户流水", notes = "by yifan")
    @RequestMapping(value = "/balancesHistory", method = RequestMethod.POST)
    public DataGrid balancesHistory(BalancesHistoryRequestVO airWallexBalancesHistoryRequestVO) {
        DataGrid dataGrid = new DataGrid();

        try {
            AirWallexRequestVO airWallexRequestVO = new AirWallexRequestVO();
            BeanUtils.copyExclude(airWallexBalancesHistoryRequestVO, airWallexRequestVO);

            //授权
            AirWallexResponseVO airWallexAuthenticationResponse = this.authentication(airWallexRequestVO);
            if (StringUtils.isNotEmpty(airWallexAuthenticationResponse.getMessage())) {
                dataGrid.setMsg("授权失败：" + airWallexAuthenticationResponse.getMessage());
                return dataGrid;
            }
            String token = airWallexAuthenticationResponse.getToken();

            StringBuilder balancesHistoryUrlNow = new StringBuilder(balancesHistoryUrl);
            if (StringUtils.isNotEmpty(airWallexBalancesHistoryRequestVO.getCurrency())) {
                balancesHistoryUrlNow = balancesHistoryUrlNow.append("?currency=").append(airWallexBalancesHistoryRequestVO.getCurrency());
            }

            Map request = getHeaderAuthorRequest(token);
            AccountRedis accountRedis = redisHashService.get(AIRWALLEXACCOUNT, airWallexBalancesHistoryRequestVO.getEmail(), AccountRedis.class);
            if (accountRedis == null) {
                dataGrid.setMsg("Redis中未找到子账户Email对应的ID");
                return dataGrid;
            }
            request.put("x-on-behalf-of", accountRedis.getId());

            log.info("请求查询子账户流水接口开始，header={}", request.toString());
            String responseStr = JsoupUtils.get(balancesHistoryUrlNow.toString(), request);
            log.info("请求查询子账户流水接口结束, responseObj={}", responseStr);

            BalancesHistoryResponse balancesHistoryResponse = JSON.parseObject(responseStr, BalancesHistoryResponse.class);
            if (StringUtils.isEmpty(balancesHistoryResponse.getMessage())) {
                dataGrid.setObj(balancesHistoryResponse);
                dataGrid.setFlag(true);
                dataGrid.setMsg("查询子账户流水成功");
            } else {
                dataGrid.setMsg("查询子账户流水失败：" + balancesHistoryResponse.getMessage());
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            dataGrid.setMsg("查询子账户流水异常");
        }
        return dataGrid;
    }

    @ApiOperation(value = "查询子账户余额", notes = "by yifan")
    @RequestMapping(value = "/balancesCurrent", method = RequestMethod.POST)
    public DataGrid balancesCurrent(BalancesCurrentRequestVO airWallexBalancesCurrentRequestVO) {
        DataGrid dataGrid = new DataGrid();
        try {
            AirWallexRequestVO airWallexRequestVO = new AirWallexRequestVO();
            BeanUtils.copyExclude(airWallexBalancesCurrentRequestVO, airWallexRequestVO);

            //授权
            AirWallexResponseVO airWallexAuthenticationResponse = this.authentication(airWallexRequestVO);
            if (StringUtils.isNotEmpty(airWallexAuthenticationResponse.getMessage())) {
                dataGrid.setMsg("授权失败：" + airWallexAuthenticationResponse.getMessage());
                return dataGrid;
            }
            String token = airWallexAuthenticationResponse.getToken();

            Map<String, String> balancesCurrentRequest = getHeaderAuthorRequest(token);
            AccountRedis accountRedis = redisHashService.get(AIRWALLEXACCOUNT, airWallexRequestVO.getEmail(), AccountRedis.class);
            if (accountRedis == null) {
                dataGrid.setMsg("Redis中未找到子账户Email对应的ID");
                return dataGrid;
            }
            balancesCurrentRequest.put("x-on-behalf-of", accountRedis.getId());

            log.info("请求查询子账户余额接口开始，header={}", balancesCurrentRequest.toString());
            String responseStr = JsoupUtils.get(balancesCurrentUrl, balancesCurrentRequest);
            log.info("请求查询子账户余额接口结束, responseObj={}", JSON.toJSONString(responseStr));

            if (responseStr.startsWith("[")) {
                List<BalancesCurrentResponse> balancesCurrentResponseList = JSONArray.parseArray(responseStr, BalancesCurrentResponse.class);
                dataGrid.setFlag(true);
                dataGrid.setRows(balancesCurrentResponseList);
                dataGrid.setMsg("查询子账户余额成功");
            } else {
                AirWallexResponseVO airWallexResponseVO = JSONObject.parseObject(responseStr, AirWallexResponseVO.class);
                dataGrid.setMsg("查询子账户余额失败：" + airWallexResponseVO.getMessage());
            }
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            dataGrid.setMsg("查询子账户余额异常：" + e.getMessage());
        }
        return dataGrid;
    }

    @ApiOperation(value = "创建子账号", notes = "by yifan")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessLicenseFile", value = "营业执照", required = true, dataType = "MultipartFile", allowMultiple = true),
            @ApiImplicitParam(name = "personalFrontFile", value = "身份证正面", required = true, dataType = "MultipartFile", allowMultiple = true),
            @ApiImplicitParam(name = "personalBackFile", value = "身份证反面", required = true, dataType = "MultipartFile", allowMultiple = true)
    })
    @RequestMapping(value = "/accountsCreate", method = RequestMethod.POST)
    public DataGrid accountsCreate(AirWallexRequestVO airWallexRequestVO, @RequestParam("businessLicenseFile") MultipartFile businessLicenseFile, @RequestParam("personalFrontFile") MultipartFile personalFrontFile, @RequestParam("personalBackFile") MultipartFile personalBackFile) {
        DataGrid dataGrid = new DataGrid();

        try {
            //1. 校验
            String message = verify(airWallexRequestVO);
            if (StringUtils.isNotEmpty(message)) {
                dataGrid.setMsg("校验失败：" + message);
                return dataGrid;
            }

            //2. 授权
            AirWallexResponseVO airWallexAuthenticationResponse = this.authentication(airWallexRequestVO);
            if (StringUtils.isNotEmpty(airWallexAuthenticationResponse.getMessage())) {
                dataGrid.setMsg("授权失败：" + airWallexAuthenticationResponse.getMessage());
                return dataGrid;
            }
            String token = airWallexAuthenticationResponse.getToken();

            //3. 文件上传
            Map requestMap = getHeaderAuthorRequest(token);
            AirWallexResponseVO businessLicenseFileResponse = filesUpload(IdentityFilesTagEnum.BUSINESS_LICENSE, requestMap, businessLicenseFile);
            if (StringUtils.isNotEmpty(businessLicenseFileResponse.getMessage())) {
                dataGrid.setMsg("营业执照上传失败：" + businessLicenseFileResponse.getMessage());
                return dataGrid;
            }
            AirWallexResponseVO personalFrontFileResponse = filesUpload(IdentityFilesTagEnum.PERSONAL_ID_FRONT, requestMap, personalFrontFile);
            if (StringUtils.isNotEmpty(personalFrontFileResponse.getMessage())) {
                dataGrid.setMsg("身份证正面上传失败：" + personalFrontFileResponse.getMessage());
                return dataGrid;
            }
            AirWallexResponseVO personalBackFileResponse = filesUpload(IdentityFilesTagEnum.PERSONAL_ID_BACK, requestMap, personalBackFile);
            if (StringUtils.isNotEmpty(personalBackFileResponse.getMessage())) {
                dataGrid.setMsg("身份证反面上传失败：" + personalBackFileResponse.getMessage());
                return dataGrid;
            }

            //4. 创建子账户
            AirWallexAccountsCreate airWallexAccountsCreate = getAccountsCreateRequest(airWallexRequestVO, businessLicenseFileResponse, personalFrontFileResponse, personalBackFileResponse);
            AirWallexResponseVO airWallexResponseVO = accountsCreate(requestMap, airWallexAccountsCreate);
            if (StringUtils.isNotEmpty(airWallexResponseVO.getMessage())) {
                dataGrid.setMsg("创建子账户失败：" + airWallexResponseVO.getMessage());
                return dataGrid;
            }

            //5. 存储Redis
            AccountRedis accountRedis = new AccountRedis();
            accountRedis.setId(airWallexResponseVO.getId());
            String email = airWallexResponseVO.getPrimary_contact().getEmail();
            accountRedis.setEmail(email);
            redisHashService.put(AIRWALLEXACCOUNT, email, accountRedis);

            dataGrid.setFlag(true);
            dataGrid.setObj(airWallexResponseVO);
            dataGrid.setMsg("上传正常");
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            dataGrid.setMsg("上传异常：" + e.getMessage());
        }
        return dataGrid;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/8 17:50
     * @Description: 创建子账号
     */
    public AirWallexResponseVO accountsCreate(Map requestMap, AirWallexAccountsCreate airWallexAccountsCreate) throws Exception {
        log.info("请求子账号创建接口开始，header={}, airWallexAccountsCreate={}", requestMap.toString(), JSON.toJSONString(airWallexAccountsCreate));
        String responseStr = JsoupUtils.post(accountsCreateUrl, requestMap, JSON.toJSONString(airWallexAccountsCreate), null);
        log.info("请求子账号创建接口结束, header={}, airWallexAccountsCreate={}", requestMap.toString(), JSON.toJSONString(airWallexAccountsCreate));

        AirWallexResponseVO airWallexResponseVO = JSONObject.parseObject(responseStr, AirWallexResponseVO.class);

        return airWallexResponseVO;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/8 16:39
     * @Description: 创建子账户请求参数
     */
    private AirWallexAccountsCreate getAccountsCreateRequest(AirWallexRequestVO airWallexRequestVO, AirWallexResponseVO businessLicenseFileResponseVO, AirWallexResponseVO personalFrontFileResponseVO, AirWallexResponseVO personalBackFileResponseVO) {
        Address address = new Address();
        address.setCountry_code(airWallexRequestVO.getCountryCode());

        List<Identity_files> identity_filesList1 = new ArrayList<Identity_files>();
        Identity_files file1 = new Identity_files();
        BeanUtils.copyExclude(businessLicenseFileResponseVO, file1);
        file1.setTag(IdentityFilesTagEnum.BUSINESS_LICENSE);
        file1.setDescription(businessLicenseFileResponseVO.getFilename());
        identity_filesList1.add(file1);

        Attachments1 attachments1 = new Attachments1();
        attachments1.setBusiness_documents(identity_filesList1);

        Business_address business_address = new Business_address();
        business_address.setCountry_code(airWallexRequestVO.getCountryCode());
        business_address.setAddress_line1(airWallexRequestVO.getAddressLine1());
        business_address.setState(airWallexRequestVO.getState());
        business_address.setSuburb(airWallexRequestVO.getSuburb());

        Business_details business_details = new Business_details();
        business_details.setAgreed_to_terms(airWallexRequestVO.getAgreedToTerms());
        business_details.setBusiness_name(airWallexRequestVO.getBusinessName());
        business_details.setAddress(address);
        business_details.setAttachments(attachments1);
        business_details.setBusiness_address(business_address);
        business_details.setIndustry_category(airWallexRequestVO.getIndustryCategoryEnum().getSpec());
        business_details.setPurpose(airWallexRequestVO.getPurposeEnum() != null ? airWallexRequestVO.getPurposeEnum().getSpec() : airWallexRequestVO.getPurpose());
        business_details.setUrl(airWallexRequestVO.getUrl());

        List<Identity_files> identity_filesList2 = new ArrayList<Identity_files>();
        Identity_files files2 = new Identity_files();
        BeanUtils.copyExclude(personalFrontFileResponseVO, files2);
        files2.setTag(IdentityFilesTagEnum.PERSONAL_ID_FRONT);
        files2.setDescription(personalFrontFileResponseVO.getFilename());
        Identity_files files3 = new Identity_files();
        BeanUtils.copyExclude(personalBackFileResponseVO, files3);
        files3.setTag(IdentityFilesTagEnum.PERSONAL_ID_BACK);
        files3.setDescription(personalBackFileResponseVO.getFilename());
        identity_filesList2.add(files2);
        identity_filesList2.add(files3);

        Attachments2 attachments2 = new Attachments2();
        attachments2.setIdentity_files(identity_filesList2);

        Legal_rep_details legal_rep_details = new Legal_rep_details();
        legal_rep_details.setAttachments(attachments2);
        legal_rep_details.setNationality(airWallexRequestVO.getNationality());

        Account_details account_details = new Account_details();
        account_details.setBusiness_details(business_details);
        account_details.setLegal_rep_details(legal_rep_details);

        Primary_contact primary_contact = new Primary_contact();
        primary_contact.setEmail(airWallexRequestVO.getEmail());

        AirWallexAccountsCreate airWallexAccountsCreate = new AirWallexAccountsCreate();
        airWallexAccountsCreate.setAccount_details(account_details);
        airWallexAccountsCreate.setPrimary_contact(primary_contact);
        airWallexAccountsCreate.setRequest_id(UUID.randomUUID().toString());

        return airWallexAccountsCreate;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/8 14:37
     * @Description: 校验
     */
    public String verify(AirWallexRequestVO airWallexRequestVO) throws Exception {
        String msg = "";

        IndustryCategoryEnum industryCategoryEnum = airWallexRequestVO.getIndustryCategoryEnum();
        String industryCategoryValue = industryCategoryEnum.getValue();
        List industryCategoryValueList = Arrays.asList(industryCategoryValue.split("/"));

        String purposeValue = "";
        if (!IndustryCategoryEnum.其他.equals(industryCategoryEnum)) {
            //PurposeEnum purposeEnum = PurposeEnum.valueOf(airWallexRequestVO.getPurpose());
            PurposeEnum purposeEnum = airWallexRequestVO.getPurposeEnum();
            purposeValue = purposeEnum.getValue();
            airWallexRequestVO.setPurposeEnum(purposeEnum);
        }

        String url = airWallexRequestVO.getUrl();

        //industryCategory/purpose/url
        if (StringUtils.isNotEmpty(purposeValue) && !industryCategoryValueList.contains(purposeValue)) {
            msg = "IndustryCategory（" + industryCategoryValue + "） 与 Purpose（" + purposeValue + "）对应出现问题";
        }
        List mustUrl = Arrays.asList(IndustryCategoryEnum.电子商务市场平台, IndustryCategoryEnum.电子商务市场批发商, IndustryCategoryEnum.数字内容和在线游戏, IndustryCategoryEnum.旅行);
        if (mustUrl.contains(industryCategoryEnum) && StringUtils.isEmpty(url)) {
            msg = "IndustryCategory（" + industryCategoryValue + "） 与 url（" + url + "）对应出现问题";
        }

        return msg;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/8 17:20
     * @Description: 文件上传
     */
    public AirWallexResponseVO filesUpload(IdentityFilesTagEnum identityFilesTagEnum, Map requestMap, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        log.info("请求上传接口开始，类型={}, heads={}", identityFilesTagEnum.getDesc(), requestMap.toString());
        String responseStr = JsoupUtils.post(filesUploadUrl, requestMap, null, multipartFile);
        log.info("请求上传接口结束, 类型={}, responseObj={}", identityFilesTagEnum.getDesc(), responseStr.toString());

        AirWallexResponseVO airWallexResponseVO = JSONObject.parseObject(responseStr, AirWallexResponseVO.class);

        return airWallexResponseVO;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/7 13:31
     * @Description: 文件上传请求参数
     */
    private Map getHeaderAuthorRequest(String token) {
        Map map = new HashMap<String, String>();
        StringBuffer sb = new StringBuffer("Bearer ");
        sb.append(token);

        map.put("Authorization", sb.toString());
        return map;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/7 13:26
     * @Description: 授权
     */
    public AirWallexResponseVO authentication(AirWallexRequestVO airWallexRequestVO) throws Exception {
        Map requestMap = getAuthenticationRequest(airWallexRequestVO);

        log.info("请求授权接口开始, heads={}", requestMap.toString());
        String responseStr = JsoupUtils.post(authenticationUrl, requestMap, null, null);
        log.info("请求授权接口结束, responseObj={}", responseStr.toString());

        AirWallexResponseVO airWallexResponseVO = JSONObject.parseObject(responseStr, AirWallexResponseVO.class);

        return airWallexResponseVO;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/7 13:31
     * @Description: 授权请求参数
     */
    public Map getAuthenticationRequest(AirWallexRequestVO airWallexRequestVO) {
        Map map = new HashMap<String, String>();
        map.put("x-api-key", airWallexRequestVO.getApiKey());
        map.put("x-client-id", airWallexRequestVO.getClientId());

        return map;
    }
}

