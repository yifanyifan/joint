package org.master.joint.utils.jsoup;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

@Slf4j
public class JsoupUtils {

    /**
     * @Author: Yifan
     * @Date: 2019/3/21 14:24
     * @Description: Jsoup
     */
    public static JSONObject sendUpload(String url, Connection.Method type, Map<String, String> heads, String paramsStr) {
        String erpUploadResponse = null;
        JSONObject jsonObject = null;
        try {
            heads.put("Accept", "*/*");
            heads.put("Content-Type", "application/json; charset=UTF-8");

            Connection connection = Jsoup.connect(url).ignoreContentType(true).headers(heads).method(type).timeout(2000000);

            if (type == Connection.Method.POST) {
                connection.requestBody(paramsStr);
            }

            erpUploadResponse = connection.execute().body();

            if (StringUtils.isEmpty(erpUploadResponse)) {
                throw new Exception("请求接口返回为空");
            }

            jsonObject = JSONObject.parseObject(erpUploadResponse);
        } catch (Exception e) {
            log.error("## 接口请求出错！URL={}，param={}, heads={}, type={}，erpUploadResponse={}", url, paramsStr, heads, type, erpUploadResponse);
            log.error("## 接口请求出错！" + e.getMessage(), e);
        }

        return jsonObject;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/3/21 14:24
     * @Description: HttpGet
     */
    public static String get(String url, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String result = null;
        try {
            HttpGet httpGet = new HttpGet(url);

            //设置超时时间
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(50000).setConnectionRequestTimeout(50000).setSocketTimeout(50000).build();
            httpGet.setConfig(defaultRequestConfig);

            //构建消息头
            if (headers != null) {
                headers.forEach((k, v) -> {
                    httpGet.setHeader(k, v);
                });
            }
            //发送请求
            HttpEntity responseEntity = httpClient.execute(httpGet).getEntity();
            if (responseEntity == null) {
                throw new Exception("接口调用失败");
            }
            // 将响应内容转换为字符串
            result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/3/21 14:24
     * @Description: HttpPost
     */
    public static String post(String url, Map<String, String> headers, String body, MultipartFile file) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String result = null;
        try {
            HttpPost httpPost = new HttpPost(url);

            //设置超时时间
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(50000).setConnectionRequestTimeout(50000).setSocketTimeout(50000).build();
            httpPost.setConfig(defaultRequestConfig);

            //构建消息头
            if (headers != null) {
                headers.forEach((k, v) -> {
                    httpPost.setHeader(k, v);
                });
            }

            // 构建消息实体
            if (body != null) {
                StringEntity entity = new StringEntity(body);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }

            //文件
            if (file != null) {
                String fileName = file.getOriginalFilename();
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);
                builder.addTextBody("filename", fileName);
                HttpEntity entity = builder.build();
                httpPost.setEntity(entity);
            }

            //发送请求
            HttpEntity responseEntity = httpClient.execute(httpPost).getEntity();
            if (responseEntity == null) {
                throw new Exception("接口调用失败");
            }
            // 将响应内容转换为字符串
            result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @Author: Yifan
     * @Date: 2019/5/10 13:45
     * @Description: HttpURLConnection方式
     * @Param path
     * @Param type "GET" | "POST"
     * @Param headers
     * @Param param JSONString
     */
    public static String httpGet(String path, String type, Map<String, String> headers, String param) {
        String inputline = "";
        String info = "";
        OutputStream out = null;

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10 * 1000);
            conn.setRequestMethod(type);

            if (headers != null) {
                for (String key : headers.keySet()) {
                    conn.setRequestProperty(key, headers.get(key));
                }
            }

            if (StringUtils.isNotEmpty(param)) {
                //获取输出流
                out = conn.getOutputStream();
                //输出流里写入POST参数
                out.write(param.getBytes());
                out.flush();
                out.close();
            }

            InputStreamReader inStream = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader buffer = new BufferedReader(inStream);
            while ((inputline = buffer.readLine()) != null) {
                info += inputline;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }
}
