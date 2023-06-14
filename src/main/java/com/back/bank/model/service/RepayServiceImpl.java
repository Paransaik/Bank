package com.back.bank.model.service;

import com.back.bank.model.dao.RepayDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepayServiceImpl implements RepayService {
    private final RepayDAO repayDAO;

    @Autowired
    private RepayServiceImpl(RepayDAO repayDAO) {
        this.repayDAO = repayDAO;
    }

    @Override
    public void repayLoan() {

    }

    @Override
    public void getRepayInfoByLoanId() {

    }

    @Override
    public void getAllRepay() {

    }

    @Override
    public void updateRepay() {

    }

    @Override
    public void deleteRepay() {

    }
}
