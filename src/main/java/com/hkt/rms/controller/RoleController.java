package com.hkt.rms.controller;

import com.hkt.rms.bean.admin.UserRole;
import com.hkt.rms.dao.UserRoleRepository;
import com.hkt.rms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleRepository roleRepository;


    @GetMapping("delete/{roleId}")
    @ResponseBody
    public Map<String, Object> deleteRole(@PathVariable int roleId) {
        return roleService.deleteRole(roleId);
    }

    @PostMapping("create")
    @ResponseBody
    public Map<String, Object> createRole(UserRole userRole) {
        return roleService.createRole(userRole);
    }

    @GetMapping("viewRoleCreate")
    public String viewCreateRole(){
        return "/admin/role/viewRoleCreate";
    }

    @PostMapping("update")
    @ResponseBody
    public Map<String, Object> updateRole(UserRole userRole) {
        return roleService.updateRole(userRole);
    }

    @GetMapping("update/{roleId}")
    public String viewRoleUpdate(Model model, @PathVariable int roleId){
        model.addAttribute("roleInfo", roleRepository.getOne(roleId));
        return "/admin/role/viewRoleUpdate";
    }

    @GetMapping("view/{roleId}")
    public String viewRoleInfo(@PathVariable int roleId,Model model) {
        model.addAttribute("roleInfo", roleRepository.getOne(roleId));
        return "/admin/role/viewRoleInfo";

    }

    @GetMapping("viewRoleList")
    public String viewRoleList(Model model) {
        model.addAttribute("roleList", roleRepository.findAll());
        return "/admin/role/viewRoleList";
    }
}
