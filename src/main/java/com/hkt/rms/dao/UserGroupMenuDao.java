package com.hkt.rms.dao;

import com.hkt.rms.bean.admin.UserGroupMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserGroupMenuDao {

    @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Map<String,Object> params = new HashMap<>();

    public void deleteByGroupId(Integer groupId) {
        String sql = "DELETE FROM user_group_menu WHERE group_id = :groupId";
        params.put("groupId",groupId);
        namedParameterJdbcTemplate.update(sql,params);
    }

    public void save(UserGroupMenu userGroupMenu) {
        String sql = "INSERT INTO user_group_menu(group_id,menu_id) VALUES (:groupId,:menuId)";
        params.put("groupId",userGroupMenu.getGroupId());
        params.put("menuId",userGroupMenu.getMenuId());
        namedParameterJdbcTemplate.update(sql,params);
    }

    public void deleteByGroupIds(int groupId, Integer[] selectedMenuList) {
        String sql = "DELETE FROM user_group_menu WHERE group_id = :groupId AND menu_id IN (:selectedMenuList)";
        params.put("groupId",groupId);
        params.put("selectedMenuList", Arrays.asList(selectedMenuList));
        namedParameterJdbcTemplate.update(sql,params);
    }
}
