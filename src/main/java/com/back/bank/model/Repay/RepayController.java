package com.back.bank.model.Repay;

import com.back.bank.model.dto.ApiResult;
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
     */
    @PostMapping("/apply")
    public ApiResult<?> repayLoan(@RequestBody Repay.Entity repay) {
        return ApiResult.succeed(repayService.repayLoan(repay));
    }

    /**
     * 특정 대출에 대한 상환 정보 조회 API
     */
    @GetMapping("/history")
    public ApiResult<?> getRepayInfoByLoanId(@RequestBody Repay.Info infoRepay) {
        return ApiResult.succeed(repayService.getRepayInfoByLoanId(
                                    infoRepay.getEmpNo(),
                                    infoRepay.getLoanDt()));
    }

    /**
     * 모든 대출에 대한 상환 정보 조회 API
     */
    @GetMapping("/history/{empNo}")
    public ApiResult<?> getRepayHistory(@PathVariable("empNo") String empNo) {
        return ApiResult.succeed(repayService.getRepayHistory(empNo));
    }
}
