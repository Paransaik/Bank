package com.back.bank.model.dao;

import com.back.bank.model.dto.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface EmployeeDAO {
	void registerEmployee(Employee.Entity employee) throws Exception;

	void updateEmployee(Map<String, String> map) throws Exception;

	void deleteEmployee(String id) throws Exception;

	Employee.Entity getEmployee(String id) throws Exception;

	Employee.Entity loginEmployee(@Param("empno") String empno, @Param("password") String password) throws Exception;

	int checkPasswordFind(Map<String, String> map) throws Exception;

	void updatePassword(Map<String, String> map) throws Exception;

}