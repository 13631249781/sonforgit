package com.hkt.rms.service;

import com.hkt.rms.bean.admin.UserMenu;
import com.hkt.rms.dao.UserMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    UserMenuRepository menuRepository;

    private static final String SELECTMENU = "SELECT menu_name,menu_uri,menu_id,action_id,description,pre_menu_id," +
            "is_menu,menu_level,sequence,icon_name FROM user_menu";

    private static final String SELECTJOIN = "SELECT um.menu_name,um.menu_uri,um.menu_id,um.action_id,um.description,um.pre_menu_id," +
            " um.is_menu,um.menu_level,um.sequence,um.icon_name FROM user_menu um ";


    public List<UserMenu> findAll() {
        List<UserMenu> menus = namedParameterJdbcTemplate.query(SELECTMENU,new BeanPropertyRowMapper<>(UserMenu.class));
        if (menus != null && menus.size() > 0) {
            return menus;
        }
        return null;
    }

    public List<UserMenu> getMenuList() {
        String sql = SELECTMENU + " WHERE menu_uri IS NOT NULL ORDER BY menu_name asc";
        //SELECT * FROM user_menu WHERE menu_uri IS NOT NULL ORDER BY menu_name asc;
        List<UserMenu> menus = namedParameterJdbcTemplate.query(sql,new BeanPropertyRowMapper<>(UserMenu.class));
        if (menus != null && menus.size() > 0) {
            return menus;
        }
        return null;
    }

    public UserMenu findUserMenubyId(Integer preId) {
        String sql = SELECTMENU+" WHERE menu_id = :preId";
        Map<String,Object> result = new HashMap<>();
        result.put("preId",preId);
        List<UserMenu> menus = namedParameterJdbcTemplate.query(sql,result,new BeanPropertyRowMapper<>(UserMenu.class));
        if (menus != null && menus.size() > 0) {
            return menus.get(0);
        }
        return null;
    }

    public List<UserMenu> findMenuByGroupId(Integer groupId) {
        String sql = "SELECT um.menu_name,um.menu_id FROM user_menu um " +
                " INNER JOIN user_group_menu ugm ON ugm.menu_id = um.menu_id " +
                " INNER JOIN user_group ug ON ugm.group_id = ug.group_id " +
                " WHERE ug.group_id = :groupId";
        Map<String,Object> result = new HashMap<>();
        result.put("groupId",groupId);
        List<UserMenu> userMenus = namedParameterJdbcTemplate.query(sql,result,new BeanPropertyRowMapper<>(UserMenu.class));
        if (userMenus != null && userMenus.size() > 0) {
            return userMenus;
        }
        return null;
    }


    //==============================================================================

    public List<UserMenu> getGroupMenuList(int groupId, int menuLevel) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT um.* FROM user_menu um LEFT JOIN user_group_menu ugm ON (um.menu_id=ugm.menu_id) ");
        sql.append("WHERE um.menu_level =:menuLevel AND ugm.group_id=:groupId ");
        sql.append("AND (um.activated_date < now() OR um.activated_date is null) ");
        sql.append("ORDER BY um.sequence,um.menu_name");
        Map<String, Object> map = new HashMap<>();
        map.put("menuLevel", menuLevel);
        map.put("groupId", groupId);
        return namedParameterJdbcTemplate.query(sql.toString(), map, (resultSet, i) -> mapUserMenu(resultSet));
    }


    public List<UserMenu> getPreMenuList() {
        String sql = "SELECT um.* FROM user_menu um WHERE um.is_menu ='Y' ORDER BY um.menu_name";
        return namedParameterJdbcTemplate.query(sql, (resultSet, i) -> mapUserMenu(resultSet));
    }

    public Map<String, Object> updateMenu(UserMenu modifyUserMenu) {
        Map<String, Object> result = new HashMap<>();
        UserMenu userMenu = menuRepository.getOne(modifyUserMenu.getMenuId());
        userMenu.setMenuName(modifyUserMenu.getMenuName());
        userMenu.setDescription(modifyUserMenu.getDescription());
        userMenu.setMenuUri(modifyUserMenu.getMenuUri());
        userMenu.setPreMenuId(modifyUserMenu.getPreMenuId());
        userMenu.setIsMenu(modifyUserMenu.getIsMenu());
        userMenu.setActivatedDate(modifyUserMenu.getActivatedDate());
        userMenu.setSequence(modifyUserMenu.getSequence());
        userMenu.setIconName(modifyUserMenu.getIconName());
        menuRepository.save(userMenu);
        result.put("msg", "The menu information is updated successfully.");
        return result;
    }

    public Map<String, Object> createMenu(UserMenu userMenu) {
        Map<String, Object> result = new HashMap<>();
        if (menuRepository.findByMenuNameAndPreMenuId(userMenu.getMenuName(), userMenu.getPreMenuId()).isPresent()) {
            result.put("type", "orange");
            result.put("msg", userMenu.getMenuName() + " under same parent menu already exists.");
        } else {
            menuRepository.save(userMenu);
            result.put("type", "green");
            result.put("msg", "The menu " + userMenu.getMenuName() + " is created successfully.");
        }
        return result;
    }

    public Map<String, Object> deleteMenu(int menuId) {
        Map<String, Object> result = new HashMap<>();
        menuRepository.deleteById(menuId);
        result.put("msg", "The menu ID " + menuId + " is deleted successfully.");
        return result;
    }

    private UserMenu mapUserMenu(ResultSet resultSet) throws SQLException {
        UserMenu um = new UserMenu();
        um.setMenuId(resultSet.getInt("menu_id"));
        um.setMenuName(resultSet.getString("menu_name"));
        um.setActionId(resultSet.getString("action_id"));
        um.setDescription(resultSet.getString("description"));
        um.setIsMenu(resultSet.getString("is_menu"));
        um.setMenuLevel(resultSet.getInt("menu_level"));
        um.setPreMenuId(resultSet.getInt("pre_menu_id"));
        um.setMenuUri(resultSet.getString("menu_uri"));
        um.setSequence(resultSet.getInt("sequence"));
        um.setActivatedDate(resultSet.getDate("activated_date"));
        um.setIconName(resultSet.getString("icon_name"));
        return um;
    }
}
