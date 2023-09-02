package com.aspire.loanmanagment.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
public class User {
    @Id
    private Long userId;
    private String username;
    private String password;
    private String role;

    public User() {

    }
}