package com.hkt.rms.controller;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.bean.admin.UserDepartment;
import com.hkt.rms.dao.UserDepartmentRepository;
import com.hkt.rms.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("dept")
public class DepartmentController {

    @Autowired
    UserDepartmentRepository userDepartmentRepository;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("viewDeptList")
    public String viewDeptList(Model model){
        model.addAttribute("deptList", userDepartmentRepository.findAll());
        return "/admin/dept/viewDeptList";
    }

    @GetMapping("view/{deptId}")
    public String viewDeptInfo(@PathVariable String deptId,Model model){
        model.addAttribute("deptInfo", userDepartmentRepository.getOne(deptId));
        return "/admin/dept/viewDeptInfo";

    }

    @GetMapping("update/{deptId}")
    public String viewDeptUpdate(@PathVariable String deptId,Model model) {
        model.addAttribute("deptInfo", userDepartmentRepository.getOne(deptId));
        return "/admin/dept/viewDeptUpdate";
    }

    @PostMapping("update")
    @ResponseBody
    public Map<String,Object> updateDept(UserDepartment userDepartment, @SessionAttribute UserStaffParticulars userBean){
        return departmentService.updateDept(userDepartment,userBean);
    }

    @GetMapping("viewDeptCreate")
    public String viewDeptCreate(){
        return "/admin/dept/viewDeptCreate";
    }

    @PostMapping("create")
    @ResponseBody
    public Map<String, Object> createDept(UserDepartment userDepartment,@SessionAttribute UserStaffParticulars userBean) {
        return departmentService.createDept(userDepartment,userBean);
    }

    @GetMapping("delete/{deptId}")
    @ResponseBody
    public Map<String, Object> deleteDept(@PathVariable String deptId) {
        return departmentService.deleteDept(deptId);
    }
}
