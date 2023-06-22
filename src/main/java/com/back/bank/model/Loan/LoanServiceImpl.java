package com.back.bank.model.Loan;

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
    public boolean reviewLoan(String empNo, Date loanDt, Loan.Type agreeYN) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return loanDAO.reviewLoan(empNo, simpleDateFormat.format(loanDt), agreeYN);
    }

    @Override
    public List<Loan.Entity> getLoanHistory(String empNo) {
        return loanDAO.getLoanHistory(empNo);
    }
}
