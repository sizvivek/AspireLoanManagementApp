package com.aspire.loanmanagment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.aspire.loanmanagment.entities.Loan;
import com.aspire.loanmanagment.entities.LoanRequest;
import com.aspire.loanmanagment.repository.LoanRepository;
import com.aspire.loanmanagment.repository.ScheduledRepaymentRepository;
import com.aspire.loanmanagment.entities.RepaymentRequest;
import com.aspire.loanmanagment.entities.ScheduledRepayment;

import java.time.LocalDate;
import java.util.*;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final ScheduledRepaymentRepository scheduledRepaymentRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository,ScheduledRepaymentRepository scheduledRepaymentRepository) {
        this.loanRepository = loanRepository;
        this.scheduledRepaymentRepository = scheduledRepaymentRepository;
    }

    public List<Loan> getPendingLoanApplications() {
        return loanRepository.findByStatus("PENDING");
    }

    public Loan approveLoanApplication(String loanApplicationId) {
        Optional<Loan> loan = loanRepository.findById(loanApplicationId);
        loan.get().setStatus("APPROVED");
        loanRepository.save(loan.get());
        return loan.get();
    }

    public Loan addRepayment(RepaymentRequest repaymentRequest) {
        Optional<Loan> loan = loanRepository.findById(repaymentRequest.getLoanId());
        double repaymentAmount = repaymentRequest.getAmount();
        ScheduledRepayment nextRepayment = getNextPendingRepayment(loan.get());
        if (repaymentAmount >= nextRepayment.getAmount()) {
            nextRepayment.setStatus("PAID");
            scheduledRepaymentRepository.save(nextRepayment);
            if (areAllRepaymentsPaid(loan.get())) {
                loan.get().setStatus("PAID");
            }
            loanRepository.save(loan.get());
        }
        return loan.get();
    }

       private ScheduledRepayment getNextPendingRepayment(Loan loan) {
        return loan.getScheduledRepayments().stream()
                .filter(repayment -> "PENDING".equals(repayment.getStatus()))
                .findFirst()
                .orElse(null);
    }

    private boolean areAllRepaymentsPaid(Loan loan) {
        return loan.getScheduledRepayments().stream()
                .allMatch(repayment -> "PAID".equals(repayment.getStatus()));
    }

    public Loan getLoanById(String loanId, String userIdFromToken) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new NoSuchElementException("No such Loan Application found"));

        // Checking if the customer's user identifier matches the loan's userId
        if (!userIdFromToken.equals(String.valueOf(loan.getUserId()))) {
            throw new AccessDeniedException("You do not have access to this loan.");
        }

        return loan;
    }

    @Override
    public List<Loan> getLoans(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    @Override
    public Loan applyForLoan(LoanRequest loanRequest) {
        Loan loan = new Loan(loanRequest.getUserId(), loanRequest.getAmount(), loanRequest.getTerm(),"PENDING", LocalDate.now());

        String loanId = UUID.randomUUID().toString();
        loan.setLoanId(loanId);

        loan.generateScheduledRepayments();
        loanRepository.save(loan);
        return loan;
    }
}

