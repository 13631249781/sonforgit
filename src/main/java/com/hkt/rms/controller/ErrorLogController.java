package com.hkt.rms.controller;

import com.hkt.rms.bean.admin.CommParaDetails;
import com.hkt.rms.bean.error.ErrorLog;
import com.hkt.rms.dao.CommParaDetailsRepository;
import com.hkt.rms.service.ErrorLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
public class ErrorLogController {

    @Autowired
    ErrorLogService errorLogService;

    @Autowired
    CommParaDetailsRepository commParaDetailsRepository;

    @GetMapping("ErrorLogList")
    public String toErrorLogList(String errorType,String orderNo,Integer status,Model model) {
        List<CommParaDetails> commParaDetails = commParaDetailsRepository.findByMasterAppCd("ERR");
        if (status == null) status = 0;
        List<ErrorLog> page = errorLogService.findAllByPage(errorType,orderNo,status);
        model.addAttribute("comm",commParaDetails);
        model.addAttribute("page",page);
        model.addAttribute("errorType",errorType);
        model.addAttribute("orderNo",orderNo);
        model.addAttribute("status",status);
        return "/order/errorLogList";
    }


}
