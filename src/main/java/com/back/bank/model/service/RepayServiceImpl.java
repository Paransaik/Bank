package com.back.bank.model.service;

import com.back.bank.model.dao.RepayDAO;
import com.back.bank.model.dto.ApiResult;
import com.back.bank.model.dto.Repay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
    public Repay.Entity getRepayInfoByLoanId(String loanId) {
        return repayDAO.getRepayInfoByLoanId(loanId);
    }

    @Override
    public List<Repay.Entity> getRepayHistory(String empNo) {
        return repayDAO.getRepayHistory(empNo);
    }

    @Override
    public void updateRepay() {

    }

    @Override
    public void deleteRepay() {

    }
}
