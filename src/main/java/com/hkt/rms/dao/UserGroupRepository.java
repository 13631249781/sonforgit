package com.hkt.rms.dao;

import com.hkt.rms.bean.admin.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup,Integer> {
    Optional<UserGroup> findByGroupName(String groupName);
}
