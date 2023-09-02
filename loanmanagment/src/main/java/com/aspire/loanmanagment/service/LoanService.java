package com.aspire.loanmanagment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aspire.loanmanagment.entities.Loan;
import com.aspire.loanmanagment.entities.LoanRequest;
import com.aspire.loanmanagment.entities.RepaymentRequest;
import com.aspire.loanmanagment.entities.ScheduledRepayment;

import java.util.List;

public interface LoanService {

    public List<Loan> getPendingLoanApplications();
    public Loan approveLoanApplication(String loanApplicationId);
    public Loan addRepayment(RepaymentRequest repaymentRequest);
    public Loan getLoanById(String loanId, String userIdFromToken);
    public List<Loan> getLoans(Long userId);
    public Loan applyForLoan(LoanRequest loanRequest);
}

