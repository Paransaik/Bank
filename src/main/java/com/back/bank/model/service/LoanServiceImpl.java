package com.back.bank.model.service;

import com.back.bank.model.dao.EmployeeDAO;
import com.back.bank.model.dao.LoanDAO;
import com.back.bank.model.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    public void getLoanStatus() {

    }

    @Override
    public void getLoanHistory() {

    }
}
