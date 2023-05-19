package com.back.miru.controller;

import com.back.miru.model.dto.FavoriteUser;
import com.back.miru.model.dto.Picture;
import com.back.miru.model.service.FavoriteService;
import com.back.miru.model.service.JwtService;
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
@RequestMapping("/favorite")
public class FavoriteController {
    public static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final JwtService jwtService;

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(JwtService jwtService, FavoriteService favoriteService) {
        this.jwtService = jwtService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("c")
    private FavoriteUser c(){
        return favoriteService.c();
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> registerFavoriteUser(@RequestBody Map<String, String> map, HttpServletRequest request) {
        System.out.println("resistFavorite controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                favoriteService.registerFavoriteUser(map);
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

    @DeleteMapping("/user/{followId}")
    public ResponseEntity<Map<String, Object>> deleteFavoriteUser(@PathVariable String followId, @RequestBody Map<String, String> map, HttpServletRequest request) {
        System.out.println("deleteFavorite controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                favoriteService.deleteFavoriteUser(followId, map);
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

    @GetMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> getFavoriteUserInfo(@PathVariable String id, HttpServletRequest request) {
        System.out.println("FavoriteInfo controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                List<FavoriteUser> favoriteUserInfo = favoriteService.infoFavoriteUser(id);
                System.out.println(favoriteUserInfo);
                resultMap.put("favoriteUserInfo", favoriteUserInfo);
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

    // 좋아요 사진 등록
    @PostMapping("/picture")
    public ResponseEntity<Map<String, Object>> registFavoritePicture(@RequestBody Map<String, String> map, HttpServletRequest request) {
        System.out.println("resistFavorite controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                favoriteService.registerFavoritePicture(map);
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

    // 좋아요 사진 제거
    @DeleteMapping("/picture/{pictureIdx}")
    public ResponseEntity<Map<String, Object>> deleteFavoritePicture(@PathVariable String pictureIdx, @RequestBody Map<String, String> map, HttpServletRequest request) {
        System.out.println("deleteFavorite controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                favoriteService.deleteFavoritePicture(pictureIdx, map);
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

    @GetMapping("/picture/{id}")
    public ResponseEntity<Map<String, Object>> getFavoritePictureInfo(@PathVariable String id, @RequestBody Map<String, String> map, HttpServletRequest request) {
        System.out.println("FavoriteInfo controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                List<Picture> favoritePictureInfo = favoriteService.infoFavoritePicture(id, map);
                resultMap.put("favoritePictureInfo", favoritePictureInfo);
                int totalPictureCnt = favoriteService.getTotalPictureCnt(id);

                resultMap.put("totalPictureCnt", totalPictureCnt);
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
