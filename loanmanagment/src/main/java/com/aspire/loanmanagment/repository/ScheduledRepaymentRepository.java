package com.aspire.loanmanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aspire.loanmanagment.entities.ScheduledRepayment;

public interface ScheduledRepaymentRepository extends JpaRepository<ScheduledRepayment, Long> {
    
}

