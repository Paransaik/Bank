package com.back.bank.model.Repay;

import java.util.Date;
import java.util.List;

public interface RepayService {
    boolean repayLoan(Repay.Entity repay);
    List<Repay.Entity> getRepayInfoByLoanId(String empNo, Date loanDt);
    List<Repay.Entity> getRepayHistory(String empNo);
}
