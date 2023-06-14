package com.back.bank.model.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

public class Repay {
    @Getter
    @ToString
    public static class Entity{
        private String empNo;
        private Date loanDt;
        private Date repayDt;
        private Long beforeLoanBalance;
        private Date loanRate;
        private Long monthRepayAmt;
        private Long repayRateAmt;
        private Date repayFrom;
        private Date repayTo;
    }
}
