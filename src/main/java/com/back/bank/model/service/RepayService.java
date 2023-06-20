package com.back.bank.model.service;

import com.back.bank.model.dto.Repay;

public interface RepayService {
    boolean repayLoan(Repay.Entity repay);
    void getRepayInfoByLoanId();
    void getAllRepay();
    void updateRepay();
    void deleteRepay();
}
