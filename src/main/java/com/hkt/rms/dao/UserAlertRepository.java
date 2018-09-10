package com.hkt.rms.dao;

import com.hkt.rms.bean.admin.UserAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAlertRepository extends JpaRepository<UserAlert,Long> {

    void deleteByUserName(String userName);
}
