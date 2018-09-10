package com.hkt.rms.dao;

import com.hkt.rms.bean.admin.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    Optional<UserRole> findByRoleNameAndRoleIdNot(String roleName, int roleId);

    Optional<UserRole> findByRoleName(String roleName);
}
