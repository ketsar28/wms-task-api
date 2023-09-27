package com.enigma.api.repository;

import com.enigma.api.entity.ReceiptNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptNumberRepository extends JpaRepository<ReceiptNumber, String> {
    ReceiptNumber findByBranchCodeAndYear(String branchCode, Integer year);
}
