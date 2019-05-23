/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.accountscreate.response;

import lombok.Data;

@Data
public class Business_address {
    private String country_code;
    private String address_line2;
    private String address_line1;
    private String suburb;
    private String state;
}