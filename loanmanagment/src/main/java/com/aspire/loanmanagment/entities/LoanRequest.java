package com.aspire.loanmanagment.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class LoanRequest {
    private Long userId;
    private double amount;
    private int term;

}