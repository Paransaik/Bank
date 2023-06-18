package com.back.bank.controller;

import com.back.bank.model.dto.ApiResult;
import com.back.bank.model.service.EmployeeService;
import com.back.bank.model.service.JwtService;
import com.back.bank.model.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

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
    @GetMapping("/info/{loanId}")
    public ApiResult<?> getLoanStatus(@PathVariable("loanId") String loanId) throws Exception {
        return ApiResult.succeed(loanService.getLoanStatus(loanId));
    }

    /**
     * 대출 이력 조회 API
     * */
    @GetMapping("/history/{userId}")
    public void getLoanHistory(){
    }
}
