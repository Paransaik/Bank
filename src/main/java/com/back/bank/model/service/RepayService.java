package com.back.bank.model.service;

import com.back.bank.model.dto.Repay;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RepayService {
    boolean repayLoan(Repay.Entity repay);
    List<Repay.Entity> getRepayInfoByLoanId(String empNo, Date loanDt);
    List<Repay.Entity> getRepayHistory(String empNo);
}
