package com.hkt.rms.service;

import com.hkt.rms.bean.admin.UserGroupMenu;
import com.hkt.rms.bean.admin.UserMenu;
import com.hkt.rms.dao.UserGroupMenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMenuService {

    @Autowired MenuService menuService;

    @Autowired
    UserGroupMenuDao userGroupMenuDao;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean saveOrUpdateGroupMenu(Integer groupId, List<Integer> menuIds) {
        List<Integer> idList = new ArrayList<>(10);
        Integer preId;
        boolean flag = false;
        try {
            userGroupMenuDao.deleteByGroupId(groupId);
            idList.addAll(menuIds);
            for (Integer menuId : menuIds) {
                preId = menuId;
                while (preId == null) {
                    UserMenu menuBean = menuService.findUserMenubyId(preId);
                    //UserMenu menuBean = userMenuRepository.getOne(preId);
                    if (menuBean.getPreMenuId() != null && idList.indexOf(preId) < 0) {
                        idList.add(menuBean.getPreMenuId());
                    }
                }
            }
            for (int i = 0; i < idList.size(); i++) {
                UserGroupMenu userGroupMenu = new UserGroupMenu();
                userGroupMenu.setGroupId(groupId);
                userGroupMenu.setMenuId(idList.get(i));
                userGroupMenuDao.save(userGroupMenu);
            }
            flag = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public boolean delete(int groupId, Integer[] selectedMenuList) {
        boolean flag = false;
        try{
            if (selectedMenuList != null) {
                userGroupMenuDao.deleteByGroupIds(groupId, selectedMenuList);
            }
            userGroupMenuDao.deleteByGroupId(groupId);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
