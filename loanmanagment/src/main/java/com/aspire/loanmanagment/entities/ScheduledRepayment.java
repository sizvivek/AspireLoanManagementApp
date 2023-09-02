package com.aspire.loanmanagment.entities;

import java.time.LocalDate;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ScheduledRepayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repaymentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loanId", nullable = false)
    private Loan loan;
    private LocalDate dueDate;
    private double amount;
    private String status;


    public ScheduledRepayment() {

    }

    public ScheduledRepayment(LocalDate dueDate, double repaymentAmount, String pending) {
                this.dueDate = dueDate;
                this.amount = repaymentAmount;
                this.status = pending;
    }

    public void setRepaymentId(Long repaymentId) {
        this.repaymentId = repaymentId;
    }
}