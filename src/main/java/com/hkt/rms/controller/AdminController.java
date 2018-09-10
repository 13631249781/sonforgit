package com.hkt.rms.controller;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.bean.admin.CommParaDetails;
import com.hkt.rms.dao.AuditTrailRepository;
import com.hkt.rms.dao.CommParaControlRepository;
import com.hkt.rms.dao.CommParaDetailsRepository;
import com.hkt.rms.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    CommParaControlRepository commParaControlRepository;

    @Autowired
    CommParaDetailsRepository commParaDetailsRepository;

    @Autowired
    AdminService adminService;

    @PostMapping("audit/searchStaff")
    public String searchAudit(int staffNum,Model model){
        model.addAttribute("auditList", auditTrailRepository.findByStaffNum(staffNum));
        return "/admin/viewAuditList";
    }

    @GetMapping("viewAuditList")
    public String viewAuditList(){
        return "/admin/viewAuditList";
    }

    @GetMapping("viewCodeList")
    public String viewCodeList(Model model) {
        model.addAttribute("parentCodeList", commParaControlRepository.findAll());
        return "/admin/code/viewCodeList";
    }

    @GetMapping("viewChild/{masterAppCode}")
    public String viewChildCodeList(@PathVariable String masterAppCode, Model model) {
        model.addAttribute("childCodeList", commParaDetailsRepository.findByMasterAppCd(masterAppCode));
        model.addAttribute("masterAppCode", masterAppCode);
        return "/admin/code/viewChildCodeList";
    }

    @GetMapping("viewChildCodeCreate/{masterAppCd}")
    public String viewChildCodeCreate(@PathVariable String masterAppCd,Model model) {
        model.addAttribute("masterAppCd", masterAppCd);
        return "/admin/code/viewChildCodeCreate";
    }

    @GetMapping("viewChildCode/{masterAppCd}/{appCd}")
    public String viewChildCodeUpdate(@PathVariable String appCd,@PathVariable String masterAppCd,Model model) {
        model.addAttribute("childCode",commParaDetailsRepository.findByAppCdAndMasterAppCd(appCd,masterAppCd).get());
        return "/admin/code/viewChildCodeUpdate";
    }
    @PostMapping("childCode/update")
    @ResponseBody
    public String updateChildCode(CommParaDetails commParaDetails){
        return adminService.updateChildCode(commParaDetails);
    }

    @PostMapping("childCode/create")
    @ResponseBody
    public Map<String,Object> createChildCode(CommParaDetails commParaDetails, @SessionAttribute UserStaffParticulars userBean){
        return adminService.createChildCode(commParaDetails,userBean);
    }

    @GetMapping("childCode/delete/{childCodeId}")
    @ResponseBody
    public Map<String, Object> deleteChildCode(@PathVariable String childCodeId) {
        return adminService.deleteChildCode(childCodeId);
    }

}
