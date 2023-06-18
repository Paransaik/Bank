package com.back.bank.model.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

public class Loan {
    @Getter
    @ToString
    public static class Entity{
        private String empNo;
        private Date loanDt;
        private float loanAmt;
        private Long monthRepayAmt;
        private Date loanRate;
        private Long loanBalance;
        private Date repayFrom;
        private Date repayTo;
        private Date lastRepayDt;
        private boolean frepayYn;
        private boolean agreeYn;
    }

    public enum Type {
        AGREE,
        REJECT
    }
}
