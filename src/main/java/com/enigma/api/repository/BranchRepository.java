package com.enigma.api.repository;

import com.enigma.api.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {
    boolean existsByBranchCode(String branchCode);

    Optional<Branch> findFirstByBranchId(String branchId);
}
