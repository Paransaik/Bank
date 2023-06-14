package com.back.bank.model.service;

import com.back.bank.model.dto.Employee;

public interface EmployeeService {
    void registerEmployee(Employee.Entity employee) throws Exception;

    boolean updateEmployee(Employee.Entity employee) throws Exception;

    boolean deleteEmployee(String id) throws Exception;

    Employee.Entity getEmployee(String empNo) throws Exception;

    Employee.Entity loginEmployee(String empNo, String passwd) throws Exception;

    int checkPasswordFind(String id, String email) throws Exception;

    void updatePassword(String id) throws Exception;

}
