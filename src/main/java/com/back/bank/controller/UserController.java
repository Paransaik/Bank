package com.back.bank.controller;

import com.back.bank.model.dto.TokenDTO;
import com.back.bank.model.dto.UserDTO;
import com.back.bank.model.service.JwtService;
import com.back.bank.model.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/user")
public class UserController {
    // DB에 저장할 Refresh Token
    static Map<String, String> refreshTokens = new HashMap<>();

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * 유저 등록
     * @author  tyJeong
     * @param   map: UserDTO
     * @return  TokenDTO
     *
     * TODO: 2023-06-08 사용자 로그인 시 이메일 중복 검사
     */
    @PostMapping
    public ResponseEntity<TokenDTO> registerUser(@RequestBody Map<String, String> map) throws Exception {
        logger.debug("Map:: {}", map.toString());
        userService.registerUser(map);
        UserDTO loginUserDTO = userService.loginUser(map.get("id"), map.get("password"));
        TokenDTO token;
        if (loginUserDTO == null) return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        token = jwtService.createAccessAndRefresh(loginUserDTO.getEmail());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * 유저 등록
     * @author  tyJeong
     * @param   map: UserDTO
     * @return  tokenDTO: access token, refresh token
     */
    @ApiOperation(value = "회원정보수정", response = Map.class)
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true)
            @PathVariable String id,
            @RequestBody Map<String, String> map) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            map.put("id", id);
            userService.updateUser(map);
            resultMap.put("userInfo", map);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    /**
     * 유저 등록
     * @author  tyJeong
     * @param   id: user id
     * @return  message
     */
    @ApiOperation(value = "회원정보삭제", response = Map.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true)
            @PathVariable String id) {
        System.out.println("delete user 호출");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        System.out.println(id);
        try {
            userService.deleteUser(id);
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
     * 유저 등록
     * @author  tyJeong
     * @param   userDTO: user id, user password
     * @return  tokenDTO: access token, refresh token
     */
    @ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true)
            UserDTO userDTO) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        // 이메일 체크 한번 해야 됨
        UserDTO loginUserDTO = userService.loginUser(userDTO.getId(), userDTO.getPassword());
        HttpStatus status;
        try {
            TokenDTO tokenDTO = jwtService.createAccessAndRefresh(loginUserDTO.getEmail());
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
     * 유저 등록
     * @author  tyJeong
     * @param   id: user id, email: user email
     * @return  cnt
     */
    @GetMapping("/{id}")
    public ResponseEntity<Integer> checkPasswordFind(
            @PathVariable String id,
            @RequestParam String email) throws Exception {
        int cnt;
        System.out.println("checkPasswordFind 실행");
        cnt = userService.checkPasswordFind(id, email);
        return new ResponseEntity<>(cnt, HttpStatus.OK);
    }

    /**
     * 유저 등록
     * @author  tyJeong
     * @param   id: user id, request
     * @return  userDTO: user id, user password
     */
    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{id}")
    public ResponseEntity<Map<String, Object>> getUserInfo(
            @PathVariable("id") @ApiParam(value = "인증할 회원의 아이디.", required = true)
            String id,
            HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                UserDTO userDTO = userService.infoUser(id);
                resultMap.put("userInfo", userDTO);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("정보 조회 실패 : {0}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            logger.error("사용 불가능 토큰!!!");
            resultMap.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
