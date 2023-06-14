package com.back.bank.model.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface LoanService {
    void applyLoan();
    void reviewLoan();
    void getLoanStatus();
    void getLoanHistory();
}
