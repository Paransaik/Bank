package com.back.bank.model.service;

import com.back.bank.model.dto.Loan;

public interface LoanService {
    void applyLoan();
    void reviewLoan();
    Loan.Entity getLoanStatus(String loanId);
    void getLoanHistory();
}
