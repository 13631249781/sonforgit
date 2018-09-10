package com.hkt.rms.controller;

import com.hkt.rms.bean.admin.UserGroup;
import com.hkt.rms.bean.admin.UserMenu;
import com.hkt.rms.service.GroupMenuService;
import com.hkt.rms.service.GroupService;
import com.hkt.rms.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("admin/groupmenu")
public class GroupMenuController {

    @Autowired
    GroupService groupService;

    @Autowired
    MenuService menuService;

    @Autowired
    GroupMenuService groupMenuService;

    @GetMapping("GroupMenuList")
    public String toGroupMenuList(Model model) {
        List<UserGroup> userGroupList = groupService.getList("UPD");
        log.debug("{}",userGroupList);
        model.addAttribute("groupMenuList", userGroupList);
        return "admin/groupMenu/groupMenuList";
    }

    @PostMapping("toGroupMenuUpdate")
    public String toGroupMenuUpdate(Integer groupId,String successMessage,String errorMessage,Model model) {
        List<UserGroup> groupList = groupService.getList("UPD");
        model.addAttribute("groupMenuList",groupList);
        if (groupId == null || groupId == 0){
            model.addAttribute("errorMessage","Please Select Group");
            return "admin/groupMenu/groupMenuList";
        }
        UserGroup group = groupService.findGroupByGroupId(groupId);
        // AC TO GROUID  FIND THAT MENUNAME
        List<UserMenu> selectedMenuList = menuService.findMenuByGroupId(groupId);

        if (StringUtils.isNotBlank(successMessage)) {
            model.addAttribute("successMessage",successMessage);
        }
        if (StringUtils.isNotBlank(errorMessage)){
            model.addAttribute("errorMessage",errorMessage);
        }
        List<UserMenu> menuList = menuService.findAll();
        model.addAttribute("selectedMenuList",selectedMenuList);
        model.addAttribute("menuList",menuList);
        model.addAttribute("group",group);
        return "admin/groupMenu/groupMenuUpdate";
    }


    @PostMapping("GroupMenuCreate")
    public String createGroupMenu (String type,Integer groupId,
                               @RequestParam(required = false,value ="selectedMenuList")List<Integer>selectedMenuList,Model model) {
        List<UserMenu>menuItemList = new ArrayList<>(10);
        List<UserGroup>groupItemList = new ArrayList<>(10);
        String successMessage = null;
        String errorMessage = null;
        if (type.equals("new"))
        {
            menuItemList = menuService.findAll();
            groupItemList = groupService.getList("ADD");
            model.addAttribute("groupMenuList", groupItemList);
            model.addAttribute("menuList",menuItemList);
            // actionList = MenuDAO.getActionItems();
            return "admin/groupMenu/groupMenuCreate";
        }
        if (groupMenuService.saveOrUpdateGroupMenu(groupId, selectedMenuList)) {
             successMessage = "The group is granted privileges for the selected menus and actions successfully.";
            model.addAttribute("successMessage",successMessage);
        }
        else {
            errorMessage = "Failed to grant privileges.";
            model.addAttribute("errorMessage",errorMessage);
        }
        groupItemList = groupService.getList("UPD");
        return "admin/groupMenu/groupMenuList";
    }



    @PostMapping("GroupMenuUpdate")
    public String groupMenuUpdate(Integer groupId, String type,
                          @RequestParam(required = false,value ="selectedMenuList") List<Integer> selectedMenuList, Model model) {
       List<UserGroup> userGroupList = groupService.getList("UPD");
       String successMessage = "";
       String errorMessage = "";
       if (groupId == null){
           return "admin/groupMenu/groupMenuList";
       }
        if (type.equals("update")){
            List<UserMenu> menuBeanList = menuService.getMenuList();
            model.addAttribute("groupMenuList", userGroupList);
            model.addAttribute("menuList",menuBeanList);
            log.debug("{}",menuBeanList);
            return "admin/groupMenu/groupMenuList";
        }else {
            if (groupMenuService.saveOrUpdateGroupMenu(groupId, selectedMenuList)) {
                successMessage = "The group is granted privileges for the selected menus and actions successfully.";
                model.addAttribute("successMessage",successMessage);
            } else {
                errorMessage = "Failed to grant privileges.";
                model.addAttribute("errorMessage",errorMessage);
            }
        }
        model.addAttribute("groupId",groupId);
        return "forward:toGroupMenuUpdate";
    }

    @PostMapping("GroupMenuDelete")
    public String groupMenuDelete(int groupId,
                                  @RequestParam(required = false,value ="selectedMenuList") Integer[] selectedMenuList,Model model) {
        if (groupMenuService.delete(groupId,selectedMenuList)){
            String successMessage = "Delete successfully.";
            model.addAttribute("successMessage",successMessage);
        }else {
            String errorMessage = "Delete Failed";
            model.addAttribute("errorMessage",errorMessage);
        }
        model.addAttribute("groupId",groupId);
        return "forward:toGroupMenuUpdate";
    }
}
