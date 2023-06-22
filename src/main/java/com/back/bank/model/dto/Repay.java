package com.back.bank.model.dto;

import lombok.Getter;

import java.util.Date;

public class Repay {
    @Getter
    public static class Entity {
        private String empNo;
        private Date loanDt;
        private Date repayDt;
        private Long beforeLoanBalance;
        private float loanRate;
        private Long monthRepayAmt;
        private Long repayRateAmt;
        private Date repayFrom;
        private Date repayTo;
    }

    @Getter
    public static class Info {
        private String empNo;
        private Date loanDt;
    }
}
