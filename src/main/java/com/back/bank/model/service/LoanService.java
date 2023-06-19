package com.back.bank.model.service;

import com.back.bank.model.dto.Loan;

import java.util.List;

public interface LoanService {
    boolean applyLoan(Loan.Entity loan);
    boolean reviewLoan(String loanId, int agreeYn);

    Loan.Entity getLoanStatus(String loanId);
    List<Loan.Entity> getLoanHistory(String empNo);
}
