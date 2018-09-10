package com.hkt.rms.dao;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.bean.admin.UserGroup;
import com.hkt.rms.bean.admin.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserStaffRepository extends JpaRepository<UserStaffParticulars,Integer> {
    Optional<UserStaffParticulars> findByStaffNumAndUserName(int staffNum, String userName);

    List<UserStaffParticulars> findByUserGroup(UserGroup one);

    List<UserStaffParticulars> findByUserRole(UserRole one);

}
