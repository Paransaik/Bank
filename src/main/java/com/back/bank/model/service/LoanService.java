package com.back.bank.model.service;

import com.back.bank.model.dto.Loan;

public interface LoanService {
    boolean applyLoan(Loan.Entity loan);
    boolean reviewLoan(String loanId, int agreeYn);

    Loan.Entity getLoanStatus(String loanId);
    void getLoanHistory();
}
