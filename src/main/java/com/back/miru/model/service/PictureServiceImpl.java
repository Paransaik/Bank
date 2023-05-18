package com.back.miru.model.service;

import com.back.miru.model.dao.PictureDAO;
import com.back.miru.model.dto.ListParameterDto;
import com.back.miru.model.dto.Picture;
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
    public List<Picture> getPictureList(Map<String, String> map) throws Exception {
        int pgno = Integer.parseInt(map.get("page"));
        int countPerPage = 20; // 한 페이지당 보여줄 개수
        int start = (pgno - 1) * countPerPage;
        String sortKeyword = map.getOrDefault("sortKeyword", "time");
        String id = map.get("id");

        ListParameterDto listParameterDto = new ListParameterDto();
        listParameterDto.setStart(start);
        listParameterDto.setCurrentPerPage(countPerPage);
        listParameterDto.setSortKeyword(sortKeyword);
        listParameterDto.setId(id);
        return pictureDAO.selectAllPictures(listParameterDto);
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
    public List<Picture> searchPictureList(String keyword, Map<String, String> map) {
        int pgno = Integer.parseInt(map.get("page"));
        int countPerPage = 20; // 한 페이지당 보여줄 개수
        int start = (pgno - 1) * countPerPage;
        String sortKeyword = map.getOrDefault("sortKeyword", "time");
        String id = map.get("id");
        boolean isPicture = "true".equals(map.getOrDefault("isPicture", null));

        ListParameterDto listParameterDto = new ListParameterDto();
        listParameterDto.setStart(start);
        listParameterDto.setCurrentPerPage(countPerPage);
        listParameterDto.setKeyword(keyword);
        listParameterDto.setSortKeyword(sortKeyword);
        listParameterDto.setIsPicture(isPicture);
        listParameterDto.setId(id);
        return pictureDAO.searchPictureList(listParameterDto);
    }

    @Override
    public Picture getPictureDetail(String pictureIdx) throws Exception {
        return pictureDAO.getPictureDetail(pictureIdx);
    }
}
