package com.back.miru.model.dao;

import com.back.miru.model.dto.FavoriteUser;
import com.back.miru.model.dto.ListParameterDto;
import com.back.miru.model.dto.Picture;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FavoriteDAO {
	void registerFavoriteUser(Map<String, String> map) throws Exception;

	void deleteFavoriteUser(Map<String, String> map) throws Exception;

	List<FavoriteUser> infoFavoriteUser(String id) throws Exception;

	void registerFavoritePicture(Map<String, String> map) throws Exception;

	void deleteFavoritePicture(Map<String, String> map) throws Exception;

	List<Picture> infoFavoritePicture(ListParameterDto listParameterDto) throws Exception;

	int getTotalPictureCnt(String id) throws Exception;
}