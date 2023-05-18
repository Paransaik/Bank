package com.back.miru.controller;

import com.back.miru.ai.TransformPainting;
import com.back.miru.model.dto.Picture;
import com.back.miru.model.service.JwtService;
import com.back.miru.model.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/picture")
public class PictureController {
    public static final Logger logger = LoggerFactory.getLogger(PictureController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PictureService pictureService;

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transferPicture(@RequestBody HashMap<String, String> map, HttpServletRequest request) {
        System.out.println("transferPicture 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                String transferPicturePath = TransformPainting.transform(map.get("optionNum"), map.get("styleFilePath"), map.get("contentFilePath"));
                resultMap.put("transferPicturePath", transferPicturePath);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("파일 명화 변경 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            logger.error("사용 불가능 토큰!!!");
            resultMap.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 전체 사진 목록 불러오기
    // selectAllPictures
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getPictureList(@RequestParam Map<String, String> map) {
        System.out.println("getPictureList controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        logger.info("사용 가능한 토큰!!!");
        try {
            List<Picture> pictureList = pictureService.getPictureList(map);
            resultMap.put("pictureList", pictureList);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("목록 불러오기 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/{keyword}")
    public ResponseEntity<Map<String, Object>> searchPicture(@PathVariable String keyword, @RequestBody Map<String, String> map) {
        System.out.println("searchPicture controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        logger.info("사용 가능한 토큰!!!");
        try {
            List<Picture> pictureList = pictureService.searchPictureList(keyword, map);
            resultMap.put("pictureList", pictureList);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보 조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 사진 디테일
    @GetMapping("/picture/{pictureIdx}")
    public ResponseEntity<Map<String, Object>> getPictureDetail(@PathVariable String pictureIdx, HttpServletRequest request) {
        System.out.println("getPictureDetail 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                Picture picture = pictureService.getPictureDetail(pictureIdx);
                Timestamp timestamp = picture.getUpdateTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                System.out.println(sdf.format(timestamp));
                resultMap.put("picture", picture);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("정보 조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            logger.error("사용 불가능 토큰!!!");
            resultMap.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 사진 등록
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> registPicture(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception {
        System.out.println("registPicture 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                pictureService.registPicture(map);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("파일 업로드 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            logger.error("사용 불가능 토큰!!!");
            resultMap.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 사진 삭제
    @DeleteMapping("/{pictureIdx}")
    public ResponseEntity<Map<String, Object>> deletePicture(@PathVariable String pictureIdx, HttpServletRequest request) {
        System.out.println("deletePicture 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("token"))) {
            logger.info("사용 가능한 토큰!!!");
            try {
                pictureService.deletePicture(pictureIdx);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("정보조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            logger.error("사용 불가능 토큰!!!");
            resultMap.put("message", FAIL);
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

//    @GetMapping("/picture/{id}")
//    public ResponseEntity<Map<String, Object>> getFavoritePictureInfo(@PathVariable String id, HttpServletRequest request) {
//        System.out.println("FavoriteInfo controller 시작");
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status;
//        if (jwtService.isUsable(request.getHeader("token"))) {
//            logger.info("사용 가능한 토큰!!!");
//            try {
//                List<pictureService> favoritePictureInfo = pictureService.infoFavoritePicture(id);
//                resultMap.put("favoritePictureInfo", favoritePictureInfo);
//                resultMap.put("message", SUCCESS);
//                status = HttpStatus.ACCEPTED;
//            } catch (Exception e) {
//                logger.error("정보조회 실패 : {}", e);
//                resultMap.put("message", e.getMessage());
//                status = HttpStatus.INTERNAL_SERVER_ERROR;
//            }
//        } else {
//            logger.error("사용 불가능 토큰!!!");
//            resultMap.put("message", FAIL);
//            status = HttpStatus.ACCEPTED;
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }
//
//    @DeleteMapping("/picture")
//    public ResponseEntity<Map<String, Object>> deleteFavoriteUser(@RequestBody Map<String, String> map, HttpServletRequest request) {
//        Map<String, Object> resultMap = new HashMap<>();
//        HttpStatus status;
//        if (jwtService.isUsable(request.getHeader("token"))) {
//            logger.info("사용 가능한 토큰!!!");
//            try {
//                pictureService.deletePicture(map);
//                resultMap.put("message", SUCCESS);
//                status = HttpStatus.ACCEPTED;
//            } catch (Exception e) {
//                logger.error("정보조회 실패 : {}", e);
//                resultMap.put("message", e.getMessage());
//                status = HttpStatus.INTERNAL_SERVER_ERROR;
//            }
//        } else {
//            logger.error("사용 불가능 토큰!!!");
//            resultMap.put("message", FAIL);
//            status = HttpStatus.ACCEPTED;
//        }
//        return new ResponseEntity<Map<String, Object>>(resultMap, status);
//    }
}
