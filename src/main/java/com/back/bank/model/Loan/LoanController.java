package com.back.bank.model.Loan;

import com.back.bank.model.dto.ApiResult;
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
     * TODO: 당일 대출 하루에 하나 조건 검사
     */
    @PostMapping("/apply")
    public ApiResult<?> applyLoan(@RequestBody Loan.Entity loan) {
        return ApiResult.succeed(loanService.applyLoan(loan));
    }

    /**
     * 심사 및 승인 API
     */
    @PutMapping("/review")
    public ApiResult<?> reviewLoan(@RequestBody Loan.Review reviewRoan) {
        return ApiResult.succeed(loanService.reviewLoan(
                                    reviewRoan.getEmpNo(),
                                    reviewRoan.getLoanDt(),
                                    reviewRoan.getAgreeYn()));
    }

    /**
     * 대출 이력 조회 API
     */
    @GetMapping("/history/{empNo}")
    public ApiResult<?> getLoanHistory(@PathVariable("empNo") String empNo) {
        return ApiResult.succeed(loanService.getLoanHistory(empNo));
    }
}
