package com.hkt.rms.dao;

import com.hkt.rms.bean.admin.UserMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMenuRepository extends JpaRepository<UserMenu,Integer> {
    Optional<UserMenu> findByMenuNameAndPreMenuId(String menuName, int preMenuId);
}
