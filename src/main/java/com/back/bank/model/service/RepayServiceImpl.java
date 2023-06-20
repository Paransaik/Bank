package com.back.bank.model.service;

import com.back.bank.model.dao.RepayDAO;
import com.back.bank.model.dto.Repay;
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
    public boolean repayLoan(Repay.Entity repay) {
        return repayDAO.repayLoan(repay);
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
