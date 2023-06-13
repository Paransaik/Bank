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
     * @param map: EmployeeDTO
     * @return TokenDTO
     * <p>
     * TODO: 2023-06-08 사용자 로그인 시 이메일 중복 검사
     * @author tyJeong
     */
    @PostMapping
    public ResponseEntity<String> registerEmployee(
            @RequestBody Employee.Entity employee) throws Exception {
        logger.debug("Map:: {}", employee);
        employeeService.registerEmployee(employee);
        Employee.Entity loginEmployee = employeeService.loginEmployee(employee.getEmpNo(), employee.getPassword());
        if (loginEmployee == null) return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        String token = jwtService.createToken(loginEmployee.getEmail(), Token.A);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * 사원 업데이트
     *
     * @param map: EmployeeDTO
     * @return tokenDTO: access token, refresh token
     * @author tyJeong
     */
    @ApiOperation(value = "회원정보수정", response = Map.class)
    @PutMapping("/{empNo}")
    public ResponseEntity<Map<String, Object>> updateEmployee(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true)
            @PathVariable String empNo,
            @RequestBody Map<String, String> map) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            map.put("empNo", empNo);
            employeeService.updateEmployee(map);
            resultMap.put("EmployeeInfo", map);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    /**
     * 사원 삭제
     *
     * @param empNo: Employee empNo
     * @return message
     * @author tyJeong
     */
    @ApiOperation(value = "회원정보삭제", response = Map.class)
    @DeleteMapping("/{empNo}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true)
            @PathVariable String empNo) {
        System.out.println("delete Employee 호출");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        System.out.println(empNo);
        try {
            employeeService.deleteEmployee(empNo);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;

        } catch (Exception e) {
            logger.error("삭제 실패 : {0}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        System.out.println(status);
        return new ResponseEntity<>(resultMap, status);
    }

    /**
     * 사원 로그인
     *
     * @param employee: Employee empNo, Employee password
     * @return tokenDTO: access token, refresh token
     * @author tyJeong
     */
    @ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginEmployee(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true)
            Employee.Entity employee) {
        Map<String, Object> resultMap = new HashMap<>();
        logger.debug(String.valueOf(employee));
        // 이메일 체크 한번 해야 됨
//        EmployeeDTO loginEmployeeDTO = employeeService.loginEmployee(EmployeeDTO.getempNo(), EmployeeDTO.getPassword());
        HttpStatus status;
        try {
            String key = employee.getEmail();
            String accessToken = jwtService.createToken(key, Token.A);
            String refreshToken = jwtService.createToken(key, Token.R);

            // Refresh Token saved DB
            refreshTokens.put(key, refreshToken);
            TokenDTO tokenDTO = TokenDTO
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .key(key)
                    .build();
            resultMap.put("token", tokenDTO);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    /**
     * 사원 패스워드 찾기
     *
     * @param empNo: Employee empNo, email: Employee email
     * @return cnt
     * @author tyJeong
     */
    @GetMapping("/{empNo}")
    public ResponseEntity<Integer> checkPasswordFind(
            @PathVariable String empNo,
            @RequestParam String email) throws Exception {
        int cnt;
        System.out.println("checkPasswordFind 실행");
        cnt = employeeService.checkPasswordFind(empNo, email);
        return new ResponseEntity<>(cnt, HttpStatus.OK);
    }

    /**
     * 사원 정보 보기
     *
     * @param empNo: Employee empNo, request
     * @return EmployeeDTO: Employee empNo, Employee password
     * @author tyJeong
     */
    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{empNo}")
    public ResponseEntity<Map<String, Object>> getEmployeeInfo(
            @PathVariable("empNo") @ApiParam(value = "인증할 회원의 아이디.", required = true) String empNo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            Employee.Entity employee = employeeService.getEmployee(empNo);
            resultMap.put("EmployeeInfo", employee);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보 조회 실패 : {0}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
