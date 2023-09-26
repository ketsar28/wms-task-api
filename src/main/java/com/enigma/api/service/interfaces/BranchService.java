package com.enigma.api.service.interfaces;

import com.enigma.api.model.request.branch.BranchRequest;
import com.enigma.api.model.response.branch.BranchResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BranchService {
    BranchResponse createBranch(BranchRequest request);
    BranchResponse getBranchById(String branchId);

    List<BranchResponse> getAllBranch(Pageable pageable);
    BranchResponse updateBranch(BranchRequest request);
    void deleteBranch(String branchId);
}
