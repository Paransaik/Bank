package com.back.bank.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/repay")
public class RepayController {

    /**
     * 상환 API
     * */
    @PostMapping("/{loanId}")
    public void repayLoan(){
    }

    /**
     * 특정 대출에 대한 상환 정보 조회 API
     */
    @GetMapping("/{loadId}")
    public void getRepayInfoByLoanId(){

    }

    /**
     * 모든 대출에 대한 상환 정보 조회 API
     */
    @GetMapping("/")
    public void getAllRepay(){

    }

    /**
     * 상환 정보 수정 API
     */
    @PutMapping()
    public void updateRepay(){

    }

    /**
     * 상환 정보 삭제 API
     */
    @DeleteMapping
    public void deleteRepay(){

    }
}
