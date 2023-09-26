package com.enigma.api.model.response.branch;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchResponse {
    private String branchId;
    private String branchCode;
    private String branchName;
    private String address;
    private String phoneNumber;
}
