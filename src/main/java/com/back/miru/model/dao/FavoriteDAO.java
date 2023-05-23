package com.back.miru.model.dao;

import com.back.miru.model.dto.FavoriteUserDTO;
import com.back.miru.model.dto.ListParameterDTO;
import com.back.miru.model.dto.PictureDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FavoriteDAO {
	void registerFavoriteUser(Map<String, String> map) throws Exception;

	void deleteFavoriteUser(Map<String, String> map) throws Exception;

	List<FavoriteUserDTO> infoFavoriteUser(String id) throws Exception;

	void registerFavoritePicture(Map<String, String> map) throws Exception;

	void deleteFavoritePicture(Map<String, String> map) throws Exception;

	List<PictureDTO> infoFavoritePicture(ListParameterDTO listParameterDTO) throws Exception;

	int getTotalPictureCnt(String id) throws Exception;
}