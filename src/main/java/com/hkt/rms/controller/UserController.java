package com.hkt.rms.controller;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.dao.AlertProfile;
import com.hkt.rms.dao.UserDepartmentRepository;
import com.hkt.rms.dao.UserGroupRepository;
import com.hkt.rms.dao.UserRoleRepository;
import com.hkt.rms.service.UserService;
import com.hkt.rms.vo.UserStaffVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Map;
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    AlertProfile alertProfile;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserDepartmentRepository userDepartmentRepository;

    @GetMapping("deleteUser/{staffNum}")
    @ResponseBody
    public Map<String, Object> deleteUser(@PathVariable int staffNum) {
        return  userService.deleteUser(staffNum);
    }

    @PostMapping("editUser")
    @ResponseBody
    public Map<String, Object> editUser(UserStaffVo userStaffVo) throws ParseException {
        return userService.editUser(userStaffVo);
    }

    @GetMapping("viewEditUser/{staffNum}")
    public String viewEditUser(@PathVariable int staffNum, Model model) {
        model.addAttribute("userInfo", userService.findUserInfo(staffNum));
        model.addAttribute("roleList", userRoleRepository.findAll());
        model.addAttribute("groupList", userGroupRepository.findAll());
        model.addAttribute("deptList", userDepartmentRepository.findAll());
        model.addAttribute("userAlertList", alertProfile.findAlertByUserName(String.valueOf(staffNum)));
        return "/user/userEdit";
    }

    @PostMapping("createUser")
    @ResponseBody
    public Map<String, Object> createUser(UserStaffVo userStaffVo, Model model) throws ParseException {
        return userService.createUser(userStaffVo);

    }

    @PostMapping("checkAvailability")
    @ResponseBody
    public Map<String, Object> checkAvailability(int staffNum, String userName, @SessionAttribute UserStaffParticulars userBean) {
        return userService.checkAvailability(staffNum, userName, userBean);
    }

    @GetMapping("viewUserCreate")
    public String viewUserCreate(Model model, @SessionAttribute UserStaffParticulars userBean) {
        model.addAttribute("roleList", userRoleRepository.findAll());
        model.addAttribute("groupList", userGroupRepository.findAll());
        model.addAttribute("deptList", userDepartmentRepository.findAll());
        model.addAttribute("userAlertList", alertProfile.findAlertByUserName(userBean.getUserName()));
        return "/user/userCreate";
    }

    @GetMapping("/view/{staffNum}")
    public String userView(@PathVariable int staffNum, Model model, @SessionAttribute UserStaffParticulars userBean) {
        model.addAttribute("userInfo", userService.findUserInfo(staffNum));
        model.addAttribute("userAlertList", alertProfile.findAlertByUserName(userBean.getUserName()));
        return "/user/userView";
    }

    @GetMapping("updateUserView")
    public String updateUserView(Model model, @SessionAttribute UserStaffParticulars userBean) {
        model.addAttribute("userAlertList", alertProfile.findAlertByUserName(userBean.getUserName()));
        return "user/updateUserView";
    }

    @PostMapping("updateUser")
    @ResponseBody
    public String updateUser(UserStaffVo modifyUserBean, Model model, @SessionAttribute UserStaffParticulars userBean, HttpSession session) {

        session.setAttribute("userBean", userService.updateUserSimpleInfo(modifyUserBean, userBean));
        model.addAttribute("userAlertList", alertProfile.findAlertByUserName(userBean.getUserName()));
        return "Success";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userBean");
        return "login";

    }


    @GetMapping("viewUserList")
    public String viewUserList(Model model) {
        model.addAttribute("userList", userService.getUserList());
        return "/user/userList";
    }
}
