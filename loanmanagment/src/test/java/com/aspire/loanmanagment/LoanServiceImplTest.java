package com.aspire.loanmanagment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.aspire.loanmanagment.entities.Loan;
import com.aspire.loanmanagment.entities.RepaymentRequest;
import com.aspire.loanmanagment.entities.ScheduledRepayment;
import com.aspire.loanmanagment.repository.LoanRepository;
import com.aspire.loanmanagment.repository.ScheduledRepaymentRepository;
import com.aspire.loanmanagment.service.LoanServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private ScheduledRepaymentRepository scheduledRepaymentRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPendingLoanApplications() {

        List<Loan> pendingLoans = new ArrayList<>();
        pendingLoans.add(LoanTestUtils.createMockLoan("1", 1000.0, 12, 1L, "PENDING"));
        pendingLoans.add(LoanTestUtils.createMockLoan("2", 2000.0, 24, 2L, "PENDING"));

        when(loanRepository.findByStatus("PENDING")).thenReturn(pendingLoans);


        List<Loan> result = loanService.getPendingLoanApplications();

        assertEquals(pendingLoans, result);
    }

    @Test
    public void testApproveLoanApplication() {

        String loanApplicationId = "1";
        Loan pendingLoan = LoanTestUtils.createMockLoan(loanApplicationId, 1000.0, 12, 1L, "PENDING");

        when(loanRepository.findById(loanApplicationId)).thenReturn(Optional.of(pendingLoan));


        Loan approvedLoan = loanService.approveLoanApplication(loanApplicationId);

        assertEquals("APPROVED", approvedLoan.getStatus());
    }

    @Test
    public void testApproveLoanApplicationAlreadyApproved() {

        String loanApplicationId = "1";
        Loan approvedLoan = LoanTestUtils.createMockLoan(loanApplicationId, 1000.0, 12, 1L, "APPROVED");

        when(loanRepository.findById(loanApplicationId)).thenReturn(Optional.of(approvedLoan));

        assertThrows(Exception.class, () -> loanService.approveLoanApplication(loanApplicationId));
    }

    @Test
    public void testAddRepayment() {
        RepaymentRequest repaymentRequest = RepaymentRequest.builder().
                                            loanId("1").
                                            amount(100.0).
                                            build();

        Loan pendingLoan =  LoanTestUtils.createMockLoan("1", 1000.0, 10, 1L, "PENDING");
        ScheduledRepayment pendingRepayment = new ScheduledRepayment();
        pendingRepayment.setRepaymentId(1L);
        pendingRepayment.setLoan(pendingLoan);
        pendingRepayment.setDueDate(LocalDate.EPOCH);
        pendingRepayment.setAmount(100.0);
        pendingRepayment.setStatus("PENDING");


        pendingLoan.getScheduledRepayments().add(pendingRepayment);

        when(loanRepository.findById(repaymentRequest.getLoanId())).thenReturn(Optional.of(pendingLoan));
        when(scheduledRepaymentRepository.save(any(ScheduledRepayment.class))).thenReturn(pendingRepayment);


        Loan updatedLoan = loanService.addRepayment(repaymentRequest);

        assertEquals("PAID", updatedLoan.getScheduledRepayments().get(0).getStatus());
        assertEquals("PAID", updatedLoan.getStatus());
    }

    @Test
    public void testAddRepaymentAlreadyPaid() {
        RepaymentRequest repaymentRequest = RepaymentRequest.builder().
                                                            loanId("1").
                                                            amount(100.0).
                                                            build();

        Loan paidLoan = LoanTestUtils.createMockLoan("1", 1000.0, 12, 1L, "PAID");

        when(loanRepository.findById(repaymentRequest.getLoanId())).thenReturn(Optional.of(paidLoan));

        assertThrows(Exception.class, () -> loanService.addRepayment(repaymentRequest));
    }


}

