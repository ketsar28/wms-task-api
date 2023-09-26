package com.enigma.api.controller;

import com.enigma.api.model.request.branch.BranchRequest;
import com.enigma.api.model.response.branch.BranchResponse;
import com.enigma.api.model.response.general.CommonResponse;
import com.enigma.api.model.response.general.PagingResponse;
import com.enigma.api.service.interfaces.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/branch")
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<?> createBranch(@RequestBody BranchRequest request) {
        BranchResponse response = branchService.createBranch(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .data(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllBranch(@PageableDefault(page = 0, size = 10, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        List<BranchResponse> responses = branchService.getAllBranch(pageable);

        PagingResponse paging = PagingResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();

        return ResponseEntity.ok(CommonResponse.builder()
                .data(responses)
                .paging(paging)
                .build());
    }

    @GetMapping(path = "/{id_branch}")
    public ResponseEntity<?> getBranchById(@PathVariable("id_branch") String branchId){
        System.out.println("branch id (controller) : " + branchId);
        BranchResponse response= branchService.getBranchById(branchId);
        return ResponseEntity.ok(CommonResponse.builder()
                .data(response)
                .build());
    }

    @PutMapping
    public ResponseEntity<?> updateBranch(@RequestBody BranchRequest request) {
        BranchResponse response = branchService.updateBranch(request);
        return ResponseEntity.ok(CommonResponse.builder()
                        .data(response)
                        .build());
    }

    @DeleteMapping(path = "/{id_branch}")
    public ResponseEntity<?> deleteBranch(@PathVariable("id_branch") String branchId) {
        branchService.deleteBranch(branchId);
        return ResponseEntity.ok(CommonResponse.builder()
                .data("OK")
                .build());
    }



}
