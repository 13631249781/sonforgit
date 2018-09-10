package com.hkt.rms.service;

import com.hkt.rms.bean.admin.UserRole;
import com.hkt.rms.dao.UserRoleRepository;
import com.hkt.rms.dao.UserStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoleService {

    @Autowired
    UserStaffRepository userStaffRepository;

    @Autowired
    UserRoleRepository roleRepository;

    public Map<String, Object> updateRole(UserRole modifyRole) {
        Map<String, Object> result = new HashMap<>();
        if (roleRepository.findByRoleNameAndRoleIdNot(modifyRole.getRoleName(),modifyRole.getRoleId()).isPresent()) {
            result.put("type", "orange");
            result.put("msg", "The role name is duplicated.");
        } else {
            UserRole role = roleRepository.getOne(modifyRole.getRoleId());
            role.setRoleName(modifyRole.getRoleName());
            role.setDescription(modifyRole.getDescription());
            role.setAddAction(modifyRole.getAddAction());
            role.setUpdateAction(modifyRole.getUpdateAction());
            role.setDeleteAction(modifyRole.getDeleteAction());
            roleRepository.save(role);
            result.put("type", "green");
            result.put("msg", "The role is updated successfully.");
        }
        return result;
    }

    public Map<String, Object> createRole(UserRole userRole) {
        Map<String, Object> result = new HashMap<>();
        if (roleRepository.findByRoleName(userRole.getRoleName()).isPresent()) {
            result.put("type", "orange");
            result.put("msg", "The role name already exists.");
        } else {
            roleRepository.save(userRole);
            result.put("type", "green");
            result.put("msg", "The role is created successfully.");
        }
        return result;
    }

    public Map<String, Object> deleteRole(int roleId) {
        Map<String, Object> result = new HashMap<>();
        if (!userStaffRepository.findByUserRole(roleRepository.getOne(roleId)).isEmpty()) {
            result.put("type", "orange");
            result.put("msg", "The role is related to some of users.<br>Please make sure no relation between role and users before deletion.");
        } else {
            roleRepository.deleteById(roleId);
            result.put("type", "green");
            result.put("msg", "The role ID " + roleId + " is deleted successfully.");
        }
        return result;
    }
}
