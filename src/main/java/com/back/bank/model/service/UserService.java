package com.back.bank.model.service;

import com.back.bank.model.dto.UserDTO;

import java.util.Map;

public interface UserService {
	void registerUser(Map<String, String> map) throws Exception;

	void updateUser(Map<String, String> map) throws Exception;

	void deleteUser(String id) throws Exception;

	UserDTO infoUser(String id) throws Exception;

	UserDTO loginUser(String id, String password) throws Exception;

	int checkPasswordFind(String id, String email) throws Exception;

	void updatePassword(String id) throws Exception;
}
