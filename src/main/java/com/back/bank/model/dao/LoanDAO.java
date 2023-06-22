package com.back.bank.model.dao;

import com.back.bank.model.dto.Loan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoanDAO {
    boolean applyLoan(Loan.Entity loan);

    boolean reviewLoan(@Param("empNo") String empNo, @Param("loanDt") String loanDt, @Param("agreeYn") Loan.Type agreeYn);

    List<Loan.Entity> getLoanHistory(String empNo);
}