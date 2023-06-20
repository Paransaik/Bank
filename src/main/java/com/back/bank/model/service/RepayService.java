package com.back.bank.model.service;

import com.back.bank.model.dto.Repay;

import java.util.List;

public interface RepayService {
    boolean repayLoan(Repay.Entity repay);
    Repay.Entity getRepayInfoByLoanId(String loanId);
    List<Repay.Entity> getRepayHistory(String empNo);
    void updateRepay();
    void deleteRepay();
}
