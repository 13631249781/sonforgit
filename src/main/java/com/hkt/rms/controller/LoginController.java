package com.hkt.rms.controller;


import com.hkt.rms.bean.AuditTrail;
import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.dao.AuditTrailRepository;
import com.hkt.rms.service.AdminService;
import com.hkt.rms.service.MenuService;
import com.hkt.rms.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    MenuService menuService;
    @Autowired
    UserService userService;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @RequestMapping("logon")
    public String logon(int userName, String txtPassword, HttpSession session, HttpServletRequest request) {
        log.debug("User name:{}",userName);
        log.debug("Password :{}",txtPassword);
        UserStaffParticulars userBean =userService.getUserInfo(userName);
        userBean.setPwdExpiryDay(99999);
        session.setAttribute("fistBeanList",menuService.getGroupMenuList(userBean.getUserGroup().getGroupId(),1));
        session.setAttribute("secondBeanList",menuService.getGroupMenuList(userBean.getUserGroup().getGroupId(),2));
        session.setAttribute("thirdBeanList",menuService.getGroupMenuList(userBean.getUserGroup().getGroupId(),3));
        session.setAttribute("userBean",userBean);
        AuditTrail at = new AuditTrail();
        at.setDescription("User successfully login.");
        at.setStaffNum(userName);
        at.setIpAddress(request.getRemoteAddr());
        at.setStartTime(new Date());
        at.setStatus("login");
        auditTrailRepository.save(at);
        log.debug("{}",at);
        return "showMenu";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }
}
