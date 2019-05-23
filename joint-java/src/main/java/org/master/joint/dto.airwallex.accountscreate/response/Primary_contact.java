/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.accountscreate.response;

import lombok.Data;

@Data
public class Primary_contact {
    private Attachments attachments;
    private Platform_connected_notification platform_connected_notification;
    private String email;
}