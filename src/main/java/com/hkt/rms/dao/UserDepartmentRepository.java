package com.hkt.rms.dao;

import com.hkt.rms.bean.admin.UserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDepartmentRepository extends JpaRepository<UserDepartment,String> {
}
