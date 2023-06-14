package com.back.bank.model.service;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface RepayService {
    public void repayLoan();
    void getRepayInfoByLoanId();
    void getAllRepay();
    void updateRepay();
    void deleteRepay();
}
