package com.back.bank.controller;

import com.back.bank.exception.ErrorCode;
import com.back.bank.model.dto.ApiResult;
import com.back.bank.model.dto.Employee;
import com.back.bank.model.dto.Token;
import com.back.bank.model.service.EmployeeService;
import com.back.bank.model.service.JwtService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/employee")
public class EmployeeController {
    // DB에 저장할 Refresh Token
    static Map<String, String> refreshTokens = new HashMap<>();

    public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final JwtService jwtService;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(JwtService jwtService, EmployeeService employeeService) {
        this.jwtService = jwtService;
        this.employeeService = employeeService;
    }

    /**
     * 사원 등록
     *
     * @param employee: Employee.Entity
     * @return Token
     * TODO: 2023-06-08 사용자 로그인 시 이메일 중복 검사
     * @author tyJeong
     */
    @PostMapping
    public ApiResult<?> registerEmployee(@RequestBody Employee.Entity employee) throws Exception {
        employeeService.registerEmployee(employee);
        return ApiResult.succeed(jwtService.createToken(employee.getEmail(), Token.Type.A));
    }

    /**
     * 사원 업데이트
     *
     * @param employee: Employee.Entity
     * @return boolean
     * @author tyJeong
     */
    @ApiOperation(value = "회원 정보 수정", response = Map.class)
    @PutMapping("/update")
    public ApiResult<?> updateEmployee(@RequestBody Employee.Entity employee) throws Exception {
        return ApiResult.succeed(employeeService.updateEmployee(employee));
    }

    /**
     * 사원 삭제
     *
     * @param empNo: Employee empNo
     * @return boolean
     * @author tyJeong
     */
    @ApiOperation(value = "회원 정보 삭제", response = Map.class)
    @DeleteMapping("/delete/{empNo}")
    public ApiResult<?> deleteEmployee(@PathVariable String empNo) throws Exception {
        return ApiResult.succeed(employeeService.deleteEmployee(empNo));
    }

    /**
     * 사원 로그인
     *
     * @param map: Employee.Entity
     * @return tokenDTO: access token, refresh token, key
     * @author tyJeong
     */
    @ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
    @PostMapping("/login")
    public ApiResult<?> loginEmployee(@RequestBody Map<String, String> map) throws Exception {
        Employee.Entity loggedEmployee = employeeService.loginEmployee(map.get("empNo"), map.get("passwd"));
        if (loggedEmployee == null) throw new NullPointerException();
//        if (loggedEmployee == null) throw new ErrorCode.ErrorException(ErrorCode.Type.UNAUTHORIZED);
        String key = map.get("email");
        String accessToken = jwtService.createToken(key, Token.Type.A);
        String refreshToken = jwtService.createToken(key, Token.Type.R);

        // Refresh Token saved DB
        refreshTokens.put(key, refreshToken);
        return ApiResult.succeed(Token.Dto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .key(key)
                .build());
    }

    /**
     * Recreate Refrsh Token
     *
     * @param
     * @return tokenDTO: access token, refresh token, key
     * @author tyJeong
     */
    @PostMapping("/getToken")
    public ApiResult<?> reCreateToken(@RequestBody Map<String, String> map) {
        String refreshToken = map.get("refreshToken");
        Claims claimsFromToken = jwtService.getClaimsFromToken(refreshToken);
        String userEmail = claimsFromToken.getSubject();

        String oddRefreshToken = refreshTokens.get(userEmail);

        // Refresh Token 만료 여부 확인
        if (claimsFromToken.getExpiration().before(new Date())){
            String accessToken = map.get("accessToken");

        }
        // Refresh Token 변조 여부 확인
        else if (!refreshToken.equals(oddRefreshToken)) {
            // 다시 로그인 할 것
            return ApiResult.failed("변조된 RefreshToken입니다.");
        }
        map.remove(refreshToken);
        String newAccessToken = jwtService.createToken(userEmail, Token.Type.A);
        String newRefreshToken = jwtService.createToken(userEmail, Token.Type.R);

        return ApiResult.succeed(Token.Dto
                .builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .key(userEmail)
                .build());
    }

    /**
     * 사원 패스워드 찾기
     *
     * @param empNo: Employee empNo, email: Employee email
     * @return cnt
     * @author tyJeong
     * TODO: 2023-06-14 수정 및 검증 할 것
     */
    @GetMapping("/{empNo}")
    public ApiResult<?> checkPasswordFind(
            @PathVariable String empNo,
            @RequestParam String email) throws Exception {
        return ApiResult.succeed(employeeService.checkPasswordFind(empNo, email));
    }

    /**
     * 사원 정보 보기
     *
     * @param empNo: Employee.Entity empNo
     * @return employee: Employee.Entity
     * @author tyJeong
     */
    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{empNo}")
    public ApiResult<?> getEmployeeInfo(@PathVariable("empNo") String empNo) throws Exception {
        return ApiResult.succeed(employeeService.getEmployee(empNo));
    }
}
