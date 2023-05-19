package com.back.miru.model.service;

import com.back.miru.model.dao.FavoriteDAO;
import com.back.miru.model.dto.FavoriteUser;
import com.back.miru.model.dto.ListParameterDto;
import com.back.miru.model.dto.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteDAO favoriteDAO;

    @Override
    public FavoriteUser c() {
        return FavoriteUser
                .builder()
                .pictureIdx(1)
                .pictureOrder(1)
                .build();
    }

    @Autowired
    public FavoriteServiceImpl(FavoriteDAO favoriteDAO) {
        this.favoriteDAO = favoriteDAO;
    }

    @Override
    public void registerFavoriteUser(Map<String, String> map) throws Exception {
        favoriteDAO.registerFavoriteUser(map);
    }

    @Override
    public void deleteFavoriteUser(String followId, Map<String, String> map) throws Exception {
        map.put("followId", followId);
        favoriteDAO.deleteFavoriteUser(map);
    }

    @Override
    public List<FavoriteUser> infoFavoriteUser(String id) throws Exception {
        return favoriteDAO.infoFavoriteUser(id);
    }

    @Override
    public void registerFavoritePicture(Map<String, String> map) throws Exception {
        favoriteDAO.registerFavoritePicture(map);
    }

    @Override
    public void deleteFavoritePicture(String pictureIdx, Map<String, String> map) throws Exception {
        map.put("pictureIdx", pictureIdx);
        favoriteDAO.deleteFavoritePicture(map);
    }

    @Override
    public List<Picture> infoFavoritePicture(String id, Map<String, String> map) throws Exception {
        int pgno = Integer.parseInt(map.get("page"));
        int countPerPage = 6; // 한 페이지당 보여줄 개수
        int start = (pgno - 1) * countPerPage;

        ListParameterDto listParameterDto = new ListParameterDto();
        listParameterDto.setStart(start);
        listParameterDto.setCurrentPerPage(countPerPage);
        listParameterDto.setId(id);
        return favoriteDAO.infoFavoritePicture(listParameterDto);
    }

    @Override
    public int getTotalPictureCnt(String id) throws Exception {
        return favoriteDAO.getTotalPictureCnt(id);
    }
}
