package com.back.miru.model.dao;

import com.back.miru.model.dto.InterestDTO;
import com.back.miru.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDAO {
	void registerUser(Map<String, String> map) throws Exception;

	void updateUser(Map<String, String> map) throws Exception;

	void deleteUser(String id) throws Exception;

	UserDTO infoUser(String id) throws Exception;

	UserDTO loginUser(@Param("id") String id, @Param("password") String password) throws Exception;

	int checkPasswordFind(Map<String, String> map) throws Exception;

	void updatePassword(Map<String, String> map) throws Exception;

	List<InterestDTO> getInterestList(String id) throws Exception;

	int registerInterest(Map<String, String> map) throws Exception;

	int deleteInterest(Map<String, String> map) throws Exception;

}