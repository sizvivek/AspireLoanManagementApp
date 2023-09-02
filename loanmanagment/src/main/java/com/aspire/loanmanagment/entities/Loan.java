package com.aspire.loanmanagment.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Loan {

    @Id
    private String loanId;
    private Long userId;
    private double amount;
    private int term;
    private LocalDate applicationDate;
    private String status;
    private LocalDate approvalDate;

    @OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "loan")
    private List<ScheduledRepayment> scheduledRepayments = new ArrayList<>();

    public Loan() {
    }

    public Loan(Long userId, double amount, int term, String pending, LocalDate applicationDate) {
        this.userId = userId;
        this.amount = amount;
        this.term = term;
        this.status = pending;
        this.applicationDate = applicationDate;
    }



    public void generateScheduledRepayments() {
        double weeklyPayment = amount / term;
        LocalDate dueDate = LocalDate.now().plusWeeks(1); 
        for (int i = 0; i < term; i++) {
            double repaymentAmount = (i == term - 1) ? amount - (weeklyPayment * i) : weeklyPayment;
            ScheduledRepayment repayment = new ScheduledRepayment(dueDate, repaymentAmount, "PENDING");
            repayment.setLoan(this);
            scheduledRepayments.add(repayment);
            dueDate = dueDate.plusWeeks(1);
        }
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}

