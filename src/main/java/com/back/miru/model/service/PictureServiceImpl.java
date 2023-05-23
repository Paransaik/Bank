package com.back.miru.model.service;

import com.back.miru.model.dao.PictureDAO;
import com.back.miru.model.dto.ListParameterDTO;
import com.back.miru.model.dto.PictureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureDAO pictureDAO;

    @Autowired
    public PictureServiceImpl(PictureDAO pictureDAO) {
        this.pictureDAO = pictureDAO;
    }

    @Override
    public List<PictureDTO> getPictureList(Map<String, String> map) throws Exception {
        int countPerPage = 20; // 한 페이지당 보여줄 개수
        return pictureDAO.selectAllPictures(ListParameterDTO
                .builder()
                .start((Integer.parseInt(map.get("page")) - 1) * countPerPage)
                .currentPerPage(countPerPage)
                .sortKeyword(map.getOrDefault("sortKeyword", "time"))
                .id(map.get("id"))
                .build());
    }

    @Override
    public void registerPicture(Map<String, String> map) throws Exception {
        pictureDAO.registPicture(map);
    }

    @Override
    public void deletePicture(String pictureIdx) throws Exception {
        pictureDAO.deletePicture(pictureIdx);
    }

    @Override
    public List<PictureDTO> searchPictureList(String keyword, Map<String, String> map) {
        int countPerPage = 20; // 한 페이지당 보여줄 개수
        return pictureDAO.searchPictureList(ListParameterDTO
                .builder()
                .start((Integer.parseInt(map.get("page")) - 1) * countPerPage)
                .currentPerPage(countPerPage)
                .keyword(keyword)
                .sortKeyword(map.getOrDefault("sortKeyword", "time"))
                .isPicture("true".equals(map.getOrDefault("isPicture", null)))
                .id(map.get("id"))
                .build());
    }

    @Override
    public PictureDTO getPictureDetail(String pictureIdx) throws Exception {
        return pictureDAO.getPictureDetail(pictureIdx);
    }
}
