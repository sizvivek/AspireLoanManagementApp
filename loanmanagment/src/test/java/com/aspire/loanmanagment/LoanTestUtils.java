package com.aspire.loanmanagment;

import com.aspire.loanmanagment.entities.Loan;
import com.aspire.loanmanagment.entities.ScheduledRepayment;

import java.util.ArrayList;
import java.util.List;

public class LoanTestUtils {

    public static Loan createMockLoan(String id, double amount, int term, Long userId, String status) {
        Loan loan = new Loan();

        loan.setUserId(userId);
        loan.setAmount(amount);
        loan.setTerm(term);
        loan.setStatus("PENDING");

        //loan.setScheduledRepayments(new ArrayList<>()); // You can set scheduled repayments if needed
        return loan;
    }

     public static ScheduledRepayment createMockScheduledRepayment(double amount, String status) {
         ScheduledRepayment scheduledRepayment = new ScheduledRepayment();

         scheduledRepayment.setAmount(amount);
         scheduledRepayment.setStatus(status);
         // Set other properties as needed
         return scheduledRepayment;
     }

     public static List<ScheduledRepayment> createMockScheduledRepayments(List<Double> amounts, String status) {
         List<ScheduledRepayment> scheduledRepayments = new ArrayList<>();
         for (int i = 0; i < amounts.size(); i++) {
             scheduledRepayments.add(createMockScheduledRepayment(amounts.get(i), status));
         }
         return scheduledRepayments;
     }
}
