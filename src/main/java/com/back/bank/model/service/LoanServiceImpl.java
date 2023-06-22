package com.back.bank.model.service;

import com.back.bank.model.dao.LoanDAO;
import com.back.bank.model.dto.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanDAO loanDAO;

    @Autowired
    private LoanServiceImpl(LoanDAO loanDAO) {
        this.loanDAO = loanDAO;
    }

    @Override
    public boolean applyLoan(Loan.Entity loan) {
        return loanDAO.applyLoan(loan);
    }

    @Override
    public boolean reviewLoan(String empNo, String loanDt, Loan.Type agreeYN) {
        return loanDAO.reviewLoan(empNo, loanDt, agreeYN);
    }

    @Override
    public Loan.Entity getLoanStatus(String empNo) {
        return loanDAO.getLoanStatus(empNo);
    }

    @Override
    public List<Loan.Entity> getLoanHistory(String empNo) {
        return loanDAO.getLoanHistory(empNo);
    }
}
