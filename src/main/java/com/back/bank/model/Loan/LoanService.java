package com.back.bank.model.Loan;

import java.util.Date;
import java.util.List;

public interface LoanService {
    boolean applyLoan(Loan.Entity loan);
    boolean reviewLoan(String empNo, Date loanDt, Loan.Type agreeYn);
    List<Loan.Entity> getLoanHistory(String empNo);
}
