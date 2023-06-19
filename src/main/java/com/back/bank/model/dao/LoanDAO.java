package com.back.bank.model.dao;

import com.back.bank.model.dto.Loan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoanDAO {
    boolean applyLoan(Loan.Entity loan);
    Loan.Entity getLoanStatus(String loanId);
    boolean reviewLoan(String loanId, int agreeYN);
    List<Loan.Entity> getLoanHistory(String empNo);
}