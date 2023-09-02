package com.aspire.loanmanagment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.aspire.loanmanagment.service.LoanService;
import com.aspire.loanmanagment.service.LoanServiceImpl;
import com.aspire.loanmanagment.entities.Loan;
import com.aspire.loanmanagment.entities.LoanRequest;
import com.aspire.loanmanagment.entities.RepaymentRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/api/loan-applications")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/admin/listPending")
    public List<Loan> getPendingLoanApplications() {
        return loanService.getPendingLoanApplications();
    }

    @PutMapping("/admin/approve/{loanApplicationId}")
    public Loan approveLoanApplication(@PathVariable String loanApplicationId) {
        return loanService.approveLoanApplication(loanApplicationId);
    }

    @PostMapping("/customer/repay")
    public Loan addRepayment(@RequestBody RepaymentRequest repaymentRequest) {
        return loanService.addRepayment(repaymentRequest);
    }

    @GetMapping("/customer/getLoanDetails/{loanId}")
    public Loan getCustomerLoanApplicationById(@PathVariable String loanId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userIdFromToken = authentication.getName();

        return loanService.getLoanById(loanId, userIdFromToken);
    }

    @GetMapping("/customer/{userId}")
    public List<Loan> getCustomerLoanApplications(@PathVariable Long userId) {
        return loanService.getLoans(userId);
    }

    @PostMapping("/customer/applyloan")
    public Loan applyForLoan(@RequestBody LoanRequest loanRequest) {
        return loanService.applyForLoan(loanRequest);
    }

}

