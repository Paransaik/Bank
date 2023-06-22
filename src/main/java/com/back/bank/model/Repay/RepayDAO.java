package com.back.bank.model.Repay;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RepayDAO {
    boolean repayLoan(Repay.Entity repay);
    List<Repay.Entity> getRepayInfoByLoanId(@Param("empNo") String empNo,
                                            @Param("loanDt") String loanDt);
	List<Repay.Entity> getRepayHistory(String empNo);
}