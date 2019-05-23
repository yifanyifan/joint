package org.master.joint.dto.airwallex.accountscreate.response;

import lombok.Data;

import java.util.List;

@Data
public class Account_details {
    private Business_details business_details;
    private List<String> director_details;
    private List<String> beneficial_owners;
    private Authorised_person_details authorised_person_details;
    private Legal_rep_details legal_rep_details;
}