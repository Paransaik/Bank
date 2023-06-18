package com.back.bank.model.service;

import com.back.bank.model.dao.LoanDAO;
import com.back.bank.model.dto.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanDAO loanDAO;

    @Autowired
    private LoanServiceImpl(LoanDAO loanDAO) {
        this.loanDAO = loanDAO;
    }

    @Override
    public void applyLoan() {

    }

    @Override
    public void reviewLoan() {

    }

    @Override
    public Loan.Entity getLoanStatus(String loanId) {
        return loanDAO.getLoanStatus(loanId);
    }

    @Override
    public void getLoanHistory() {

    }
}
