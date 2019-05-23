/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.accountscreate.response;

import lombok.Data;

import java.util.List;

@Data
public class Attachments {
    private List<String> identity_files;
}