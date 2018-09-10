package com.hkt.rms.service;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.bean.admin.UserDepartment;
import com.hkt.rms.dao.UserDepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    UserDepartmentRepository departmentRepository;

    public Map<String, Object> updateDept(UserDepartment userDepartment, UserStaffParticulars userBean) {
        Map<String, Object> result = new HashMap<>();
        UserDepartment dept=departmentRepository.getOne(userDepartment.getDeptId());
        dept.setDeptName(userDepartment.getDeptName());
        dept.setLastUpdDate(new Date());
        dept.setLastUpdId(String.valueOf(userBean.getStaffNum()));
        departmentRepository.save(dept);
        result.put("type", "green");
        result.put("msg", "The department information is updated successfully.");
        return result;
    }

    public Map<String, Object> createDept(UserDepartment userDepartment, UserStaffParticulars userBean) {
        Map<String, Object> result = new HashMap<>();
        if (departmentRepository.findById(userDepartment.getDeptId()).isPresent()) {
            result.put("type", "orange");
            result.put("msg", "This department ID already exists.");
        } else {
            userDepartment.setLastUpdId(String.valueOf(userBean.getStaffNum()));
            userDepartment.setCreateDate(new Date());
            userDepartment.setLastUpdDate(new Date());
            departmentRepository.save(userDepartment);
            result.put("type", "green");
            result.put("msg", "The department information is created successfully.");
        }
        return result;
    }

    public Map<String, Object> deleteDept(String deptId) {
        Map<String, Object> result = new HashMap<>();
        departmentRepository.deleteById(deptId);
        result.put("type", "green");
        result.put("msg", "The department ID " + deptId + " is deleted successfully.");
        return result;
    }
}
