package com.back.bank.controller;

import com.back.bank.model.dao.RepayDAO;
import com.back.bank.model.dto.ApiResult;
import com.back.bank.model.dto.Loan;
import com.back.bank.model.dto.Repay;
import com.back.bank.model.service.RepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/repay")
public class RepayController {

    private final RepayService repayService;

    @Autowired
    public RepayController(RepayService repayService) {
        this.repayService = repayService;
    }

    /**
     * 상환 API
     * */
    @PostMapping("/apply")
    public ApiResult<?> repayLoan(@RequestBody Repay.Entity repay){
        return ApiResult.succeed(repayService.repayLoan(repay));
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
