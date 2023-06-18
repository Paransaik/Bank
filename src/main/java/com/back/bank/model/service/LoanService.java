package com.back.bank.model.service;

import com.back.bank.model.dto.Loan;

public interface LoanService {
    boolean applyLoan(String loanId);
    boolean reviewLoan(String loanId, int agreeYN);

    Loan.Entity getLoanStatus(String loanId);
    void getLoanHistory();
}
