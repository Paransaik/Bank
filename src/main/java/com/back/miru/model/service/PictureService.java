package com.back.miru.model.service;

import com.back.miru.model.dto.PictureDTO;

import java.util.List;
import java.util.Map;

public interface PictureService {
    List<PictureDTO> getPictureList(Map<String, String> map) throws Exception;

    void registerPicture(Map<String, String> map) throws Exception;

    void deletePicture(String pictureIdx) throws Exception;

    List<PictureDTO> searchPictureList(String keyword, Map<String, String> map);

    PictureDTO getPictureDetail(String pictureIdx) throws Exception;
}
