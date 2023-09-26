package com.enigma.api.model.request.branch;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchRequest {
    private String branchId;
    @NotBlank(message = "branchCode is required")
    private String branchCode;
    @NotBlank(message = "branchName is required")
    private String branchName;
    private String address;
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;
}
