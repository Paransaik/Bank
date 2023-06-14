package com.back.bank.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/loan")
public class LoanController {

    /**
     * 대출 신청 API
     * */
    @PostMapping("/apply")
    public void applyLoan(){
    }

    /**
     * 심사 및 승인 API
     * */
    @PostMapping("{loanId}/review")
    public void reviewLoan(){
    }

    /**
     * 대출 상태 조회 API
     */
    @GetMapping("/{loanId}")
    public void getLoanStatus(){
    }

    /**
     * 대출 이력 조회 API
     * */
    @GetMapping("/history/{userId}")
    public void getLoanHistory(){
    }
}
