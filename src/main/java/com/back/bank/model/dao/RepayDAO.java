package com.back.bank.model.dao;

import com.back.bank.model.dto.Repay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepayDAO {
    boolean repayLoan(Repay.Entity repay);
    Repay.Entity getRepayInfoByLoanId(String loanId);
	List<Repay.Entity> getRepayHistory(String empNo);
}