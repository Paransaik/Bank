package com.back.bank.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDAO employeeDao;

    @Autowired
    private EmployeeServiceImpl(EmployeeDAO employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public void registerEmployee(Employee.Entity employee) throws Exception {
        employeeDao.registerEmployee(employee);
    }

    @Override
    public boolean updateEmployee(Employee.Entity employee) throws Exception {
        return employeeDao.updateEmployee(employee);
    }

    @Override
    public boolean deleteEmployee(String empNo) throws Exception {
        return employeeDao.deleteEmployee(empNo);
    }

    @Override
    public Employee.Entity getEmployee(String empNo) throws Exception {
        return employeeDao.getEmployee(empNo);
    }

    @Override
    public Employee.Entity loginEmployee(String empNo, String passwd) throws Exception {
        return employeeDao.loginEmployee(empNo, passwd);
    }

    @Override
    public int checkPasswordFind(String empNo, String email) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", empNo);
        map.put("email", email);
        int cnt = employeeDao.checkPasswordFind(map);
        if (cnt == 1) {
            map.put("salt", randomGenerateString(16));
            String newPassword = randomGenerateString(5);
            map.put("password", newPassword);
            employeeDao.updatePassword(map);
        }
        return cnt;
    }

    @Override
    public void updatePassword(String empNo) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", empNo);
        map.put("password", randomGenerateString(8));
        map.put("salt", randomGenerateString(16));
        employeeDao.updatePassword(map);
    }

    public String randomGenerateString(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        return random
                .ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
