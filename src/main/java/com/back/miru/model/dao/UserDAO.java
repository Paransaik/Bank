package com.back.miru.model.dao;

import com.back.miru.model.dto.InterestDTO;
import com.back.miru.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserDAO {
	void registerUser(Map<String, String> map) throws SQLException;

	void updateUser(Map<String, String> map) throws SQLException;

	void deleteUser(String id) throws SQLException;

	UserDTO infoUser(String id) throws SQLException;

	UserDTO loginUser(String id, String password) throws SQLException;

	int checkPasswordFind(Map<String, String> map) throws SQLException;

	void updatePassword(Map<String, String> map) throws SQLException;

	List<InterestDTO> getInterestList(String id) throws Exception;

	int registerInterest(Map<String, String> map) throws Exception;

	int deleteInterest(Map<String, String> map) throws Exception;

}