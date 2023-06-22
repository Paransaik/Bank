package com.back.bank.model.service;

import com.back.bank.model.dao.RepayDAO;
import com.back.bank.model.dto.ApiResult;
import com.back.bank.model.dto.Repay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public List<Repay.Entity> getRepayInfoByLoanId(String empNo, Date loanDt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return repayDAO.getRepayInfoByLoanId(empNo,
                simpleDateFormat.format(loanDt));
    }

    @Override
    public List<Repay.Entity> getRepayHistory(String empNo) {
        return repayDAO.getRepayHistory(empNo);
    }
}
