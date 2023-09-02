package com.aspire.loanmanagment.entities;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class RepaymentRequest {
    private String loanId;
    private double amount;
}
