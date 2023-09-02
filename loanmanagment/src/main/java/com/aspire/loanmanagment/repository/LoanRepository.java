package com.aspire.loanmanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aspire.loanmanagment.entities.Loan;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, String> {
    List<Loan> findByStatus(String status);
    List<Loan> findByUserId(Long userId);
}

