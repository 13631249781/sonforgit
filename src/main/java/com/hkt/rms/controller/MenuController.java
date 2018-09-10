package com.hkt.rms.controller;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.bean.admin.UserMenu;
import com.hkt.rms.dao.UserMenuRepository;
import com.hkt.rms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping("menu")
@Controller
public class MenuController {

    @Autowired
    MenuService menuService;

    @Autowired
    UserMenuRepository menuRepository;

    @GetMapping("delete/{menuId}")
    @ResponseBody
    public Map<String, Object> deleteMenu(@PathVariable int menuId) {
        return menuService.deleteMenu(menuId);
    }

    @PostMapping("create")
    @ResponseBody
    public Map<String, Object> createMenu(UserMenu userMenu) {
        return menuService.createMenu(userMenu);
    }

    @GetMapping("viewMenuCreate")
    public String viewMenuCreate(Model model) {
        model.addAttribute("preMenuList", menuService.getPreMenuList());
        return "/admin/menu/viewMenuCreate";
    }

    @PostMapping("update")
    @ResponseBody
    public Map<String,Object> updateMenu(UserMenu userMenu){
        return menuService.updateMenu(userMenu);

    }

    @GetMapping("update/{menuId}")
    public String viewMenuUpdate(Model model,@PathVariable int menuId) {
        model.addAttribute("menuInfo", menuRepository.getOne(menuId));
        model.addAttribute("preMenuList", menuService.getPreMenuList());
        return "/admin/menu/viewMenuUpdate";
    }


    @GetMapping("view/{menuId}")
    public String viewMenuInfo(@PathVariable int menuId,Model model) {
        model.addAttribute("menuInfo", menuRepository.getOne(menuId));
        return "/admin/menu/viewMenuInfo";

    }

    @GetMapping("viewMenuList")
    public String viewMenuList(Model model) {
        model.addAttribute("menuList", menuRepository.findAll());
        return "/admin/menu/viewMenuList";
    }

    @GetMapping("showMenu")
    public String showMenu(@SessionAttribute UserStaffParticulars userBean, HttpSession session) {
        session.setAttribute("fistBeanList",menuService.getGroupMenuList(userBean.getUserGroup().getGroupId(),1));
        session.setAttribute("secondBeanList",menuService.getGroupMenuList(userBean.getUserGroup().getGroupId(),2));
        session.setAttribute("thirdBeanList",menuService.getGroupMenuList(userBean.getUserGroup().getGroupId(),3));
        return "showMenu";
    }
}
