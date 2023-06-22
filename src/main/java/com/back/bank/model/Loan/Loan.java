package com.back.bank.model.Loan;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

public class Loan {
    @Getter
    public static class Entity {
        private String empNo;
        private Date loanDt;
        private Long loanAmt;
        private Long monthRepayAmt;
        private float loanRate;
        private Long loanBalance;
        private Date repayFrom;
        private Date repayTo;
        private Date lastRepayDt;
        private boolean repayYn;
        private Type agreeYn;
    }

    public enum Type {
        AGREE,
        REJECT
    }

    @Getter
    public static class Review {
        private String empNo;
        private Date loanDt;
        private Type agreeYn;
    }
}
