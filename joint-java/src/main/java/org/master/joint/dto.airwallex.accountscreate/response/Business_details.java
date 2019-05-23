/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.accountscreate.response;

import lombok.Data;

@Data
public class Business_details {
    private String business_name;
    private Address address;
    private boolean agreed_to_terms;
    private Attachments attachments;
    private String industry_category;
    private Business_address business_address;
    private String purpose;
    private boolean opt_in_for_marketing;
    private String url;
}