package com.back.bank.controller;

import com.back.bank.model.dto.Employee;
import com.back.bank.model.dto.Token;
import com.back.bank.model.dto.TokenDTO;
import com.back.bank.model.service.EmployeeService;
import com.back.bank.model.service.JwtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/employee")
public class EmployeeController {
    // DB에 저장할 Refresh Token
    static Map<String, String> refreshTokens = new HashMap<>();

    public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

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
     * @return TokenDTO
     * TODO: 2023-06-08 사용자 로그인 시 이메일 중복 검사
     * @author tyJeong
     */
    @PostMapping
    public ResponseEntity<String> registerEmployee(@RequestBody Employee.Entity employee) throws Exception {
        employeeService.registerEmployee(employee);
        String token = jwtService.createToken(employee.getEmail(), TokenDTO.Type.A);
        return new ResponseEntity<>(token, HttpStatus.OK);
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
    public boolean updateEmployee(@RequestBody Employee.Entity employee) throws Exception {
        return employeeService.updateEmployee(employee);
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
    public boolean deleteEmployee(@PathVariable String empNo) throws Exception {
        return employeeService.deleteEmployee(empNo);
    }

    /**
     * 사원 로그인
     *
     * @param employee: Employee.Entity
     * @return tokenDTO: access token, refresh token, key
     * @author tyJeong
     */
    @ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
    @PostMapping("/login")
    public TokenDTO loginEmployee(Employee.Entity employee) throws Exception {
        Employee.Entity loggedEmployee = employeeService.loginEmployee(employee.getEmpNo(), employee.getPasswd());
        if (loggedEmployee == null) throw new NullPointerException();
        String key = employee.getEmail();
        String accessToken = jwtService.createToken(key, TokenDTO.Type.A);
        String refreshToken = jwtService.createToken(key, TokenDTO.Type.R);

        // Refresh Token saved DB
        refreshTokens.put(key, refreshToken);
        return TokenDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .key(key)
                .build();
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
    public int checkPasswordFind(
            @PathVariable String empNo,
            @RequestParam String email) throws Exception {
        return employeeService.checkPasswordFind(empNo, email);
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
    public Employee.Entity getEmployeeInfo(@PathVariable("empNo") String empNo) throws Exception {
        return employeeService.getEmployee(empNo);
    }
}
