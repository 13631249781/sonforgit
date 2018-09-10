package com.hkt.rms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.bean.admin.SiteInterfaceBean;
import com.hkt.rms.service.SiteInterfaceService;


@Controller
@Slf4j
@RequestMapping("/admin/siteinterface")
public class SiteInterfaceController {
    
    @Autowired
    private SiteInterfaceService siteInterfaceService;    
    
    @GetMapping("/SiteInterfaceList")
    public String list(Model model,String message){
        List<SiteInterfaceBean> resultList =  siteInterfaceService.findAll(); 
        model.addAttribute("resultList", resultList);
        if(message!=null)
            model.addAttribute("message", message);
        return "/admin/siteinterface/SiteInterfaceList";
    }

    @GetMapping("/SiteInterface/{systemName}")
    public String edit(Model model,@PathVariable("systemName")String systemName){
        SiteInterfaceBean resultBean = siteInterfaceService.find(systemName);
        model.addAttribute("resultBean", resultBean);
        return "/admin/siteinterface/SiteInterfaceUpdate";
    }
    
    @PostMapping("/SiteInterface")
    public String update(Model model,SiteInterfaceBean siteInterfaceBean,HttpSession session){
     
      
        String message="";
        try
        {
            if(siteInterfaceService.find(siteInterfaceBean.getSystemName())!=null){
                siteInterfaceBean.setLastUpdatedDate(new Date());
                siteInterfaceBean.setLastUpdatedBy(((UserStaffParticulars)session.getAttribute("userBean")).getUserName());
                siteInterfaceService.update(siteInterfaceBean);
                message = "The site interface information is updated successfully.";
          //FIXME  Aopg NossrsInterface.refresh(Constants.PARA_SITE_INTERFACE);
                
            }
            else
               message="Failed to update web service due to null SystemName";
        }
        catch(Exception e)
        {
               message = "Failed to update web service due to " + e.getMessage();
        }
        
        return "redirect:/admin/siteinterface/SiteInterfaceList?message=" + message;
        
    }
    
}
