package com.back.miru.model.service;

import com.back.miru.model.dto.Interest;
import com.back.miru.model.dto.User;

import java.util.List;
import java.util.Map;

public interface UserService {
	void registerUser(Map<String, String> map) throws Exception;

	void updateUser(Map<String, String> map) throws Exception;

	void deleteUser(String id) throws Exception;

	User infoUser(String id) throws Exception;

	User loginUser(String id, String password) throws Exception;

	int checkPasswordFind(String id, String email) throws Exception;

	void updatePassword(String id) throws Exception;

	List<Interest> getInterestList(String id) throws Exception;

	boolean resisterInterest(Map<String, String> map) throws Exception;

	boolean deleteInterest(Map<String, String> map) throws Exception;
}
