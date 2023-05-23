package com.back.miru.model.dao;

import com.back.miru.model.dto.ListParameterDTO;
import com.back.miru.model.dto.PictureDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PictureDAO {

    // getPictureList
    List<PictureDTO> selectAllPictures(ListParameterDTO listParameterDto) throws Exception;

    void registPicture(Map<String, String> map) throws Exception;

    void deletePicture(String pictureIdx) throws Exception;

    List<PictureDTO> searchPictureList(ListParameterDTO listParameterDto);

    PictureDTO getPictureDetail(String pictureIdx) throws Exception;

}