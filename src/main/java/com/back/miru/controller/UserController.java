package com.back.miru.controller;

import com.back.miru.model.dto.Interest;
import com.back.miru.model.dto.User;
import com.back.miru.model.service.JwtService;
import com.back.miru.model.service.UserService;
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
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/user")
public class UserController {
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

    @GetMapping("/check/{id}")
    public int checkId(@PathVariable String id) throws Exception {
        System.out.println("checkId controller 시작");
        return userService.checkId(id);
    }

    @PostMapping
    public ResponseEntity<String> registUser(@RequestBody Map<String, String> map) throws Exception {
        System.out.println("resister controller 시작");
        userService.registerUser(map);
        System.out.println("map : " + map);
        User loginUser = userService.loginUser(map.get("id"), map.get("password"));
        String token = "";
        if (loginUser != null) {
            token = jwtService.create("id", loginUser.getId(), "token");
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(token, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "회원정보수정", response = Map.class)
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) @PathVariable String id,
            @RequestBody Map<String, String> map) {
        System.out.println("update User 호출");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;

        try {
            map.put("id", id);
            userService.updateUser(map);
            resultMap.put("userInfo", map);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;

        } catch (Exception e) {
            logger.error("수정 실패 : {0}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "회원정보삭제", response = Map.class)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) @PathVariable String id) {
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

    @ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(
            @RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) User user) {
        System.out.println("login contoller 호출");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            User loginUser = userService.loginUser(user.getId(), user.getPassword());
            if (loginUser != null) {
                String token = jwtService.create("id", loginUser.getId(), "token");// key, data, subject
                logger.debug("로그인 토큰정보 : {}", token);
                resultMap.put("token", token);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } else {
                resultMap.put("message", FAIL);
                status = HttpStatus.ACCEPTED;
            }
        } catch (Exception e) {
            logger.error("로그인 실패 : {0}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Integer> checkPasswordFind(@PathVariable String id, @RequestParam String email) throws Exception {
        int cnt;
        System.out.println("checkPasswordFind 실행");
        cnt = userService.checkPasswordFind(id, email);
        return new ResponseEntity<>(cnt, HttpStatus.OK);
    }

    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{id}")
    public ResponseEntity<Map<String, Object>> getUserInfo(
            @PathVariable("id") @ApiParam(value = "인증할 회원의 아이디.", required = true) String id,
            HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                User user = userService.infoUser(id);
                resultMap.put("userInfo", user);
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

    @GetMapping("/interest/{id}")
    public ResponseEntity<List<Interest>> getInterestList(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(userService.getInterestList(id), HttpStatus.OK);
    }

    @PostMapping("/interest")
    public ResponseEntity<String> addInterest(@RequestBody Map<String, String> map) throws Exception {
        if (userService.resisterInterest(map)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/interest/remove")
    public ResponseEntity<String> deleteInterest(@RequestBody Map<String, String> map) throws Exception {
        if (userService.deleteInterest(map)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
