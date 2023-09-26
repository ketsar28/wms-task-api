package com.enigma.api.service.impl;

import com.enigma.api.entity.Branch;
import com.enigma.api.model.request.branch.BranchRequest;
import com.enigma.api.model.response.branch.BranchResponse;
import com.enigma.api.repository.BranchRepository;
import com.enigma.api.service.interfaces.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;


    private static BranchResponse toBranchResponse(Branch branch) {
        return BranchResponse.builder()
                .branchId(branch.getBranchId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .address(branch.getAddress())
                .phoneNumber(branch.getPhoneNumber())
                .build();
    }

    @Override
    public BranchResponse createBranch(BranchRequest request) {

        if(branchRepository.existsByBranchCode(request.getBranchCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "data duplicated!");
        }

        Branch branch = new Branch();
        branch.setBranchId(request.getBranchId());
        branch.setBranchCode(request.getBranchCode());
        branch.setBranchName(request.getBranchName());
        branch.setAddress(request.getAddress());
        branch.setPhoneNumber(request.getPhoneNumber());

        branchRepository.saveAndFlush(branch);
        return toBranchResponse(branch);
    }

    @Override
    public BranchResponse getBranchById(String branchId) {
        System.out.println("branch id (service) : " + branchId);
        Branch branch = branchRepository.findFirstByBranchId(branchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "branch is not found"));
        return toBranchResponse(branch);
    }

    @Override
    public List<BranchResponse> getAllBranch(Pageable pageable) {
        Page<Branch> branchPage =  branchRepository.findAll(pageable);
        List<Branch> branches = branchPage.getContent();

        return branches.stream()
                .map(BranchServiceImpl::toBranchResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BranchResponse updateBranch(BranchRequest request) {
        Branch branch = branchRepository.findById(request.getBranchId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "branch is not found"));

        branch.setBranchId(request.getBranchId());
        branch.setBranchCode(request.getBranchCode());
        branch.setBranchName(request.getBranchName());
        branch.setAddress(request.getAddress());
        branch.setPhoneNumber(request.getPhoneNumber());

        branchRepository.saveAndFlush(branch);

        return toBranchResponse(branch);
    }

    @Override
    public void deleteBranch(String branchId) {
        branchRepository.deleteById(branchId);
    }
}
