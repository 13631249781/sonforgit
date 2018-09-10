package com.hkt.rms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.bean.admin.HousekeepCommand;
import com.hkt.rms.service.HousekeepCommandService;

@Controller
@Slf4j
@RequestMapping("/admin/housekeepcommand/")
public class HousekeepCommandController {

    @Autowired
    private HousekeepCommandService housekeepCommandService;

    @GetMapping("HousekeepCommandList")
    public String list(Model model,String message) {
        //?
        List<HousekeepCommand> housekeepCommandList = housekeepCommandService.getHousekeepCommandList();
        model.addAttribute("housekeepCommandList", housekeepCommandList);
        model.addAttribute("message", message);
        return "admin/housekeepCommand/housekeepCommandList";
    }

    
    @GetMapping("HousekeepCommand/{act}/{description}")
    public String view(Model model,@PathVariable("description")String description,@PathVariable("act")String act) {
      
        try 
        {
            HousekeepCommand housekeepCommand = housekeepCommandService.find(description);
            model.addAttribute("housekeepCommandBean", housekeepCommand);
        } 
        catch (Exception e) 
        {
            String message = "Fail to obtain housekeep command due to " + e.getMessage();
            model.addAttribute("message", message);
        }

        if("view".equals(act))
        return "admin/housekeepCommand/housekeepCommandView";
        else    //"update.equals(act)"
        return "admin/housekeepCommand/housekeepCommandUpdate";
    }
    

    @GetMapping("/HousekeepCommand")
    public String toAddPage() {

        return "admin/housekeepCommand/housekeepCommandCreate";
    }

    @PutMapping("/HousekeepCommand")
    public String update(HousekeepCommand housekeepCommand,HttpSession session){
        String message = "";
        try
        {  
            String username = ((UserStaffParticulars) session.getAttribute("userBean")).getUserName();
            housekeepCommand.setLastUpdatedDate(new Date());
            housekeepCommand.setLastUpdatedBy(username);
            housekeepCommand.setEnableInd("Y".equals(housekeepCommand.getEnableInd())?"Y":"N");
            HousekeepCommand hc = housekeepCommandService.update(housekeepCommand);
            if(hc == null)
            message = "Fail to update housekeep command";
            message = "Housekeep command has been updated successfully.";
        }
        catch(Exception e)
        {
            message = "Fail to update housekeep command due to " + e.getMessage();
        }
        
        return "redirect:/admin/housekeepcommand/HousekeepCommandList?message="+message;
    }
    
    
    @PostMapping("/HousekeepCommand")
    public String add(HousekeepCommand housekeepCommand, HttpSession session, Model  model) {
        String message = "";
        if (housekeepCommand.getDescription() == null || housekeepCommand.getDescription().length()>50)
        {
            message = "Fail to add due to param incorrect ";
            model.addAttribute("message",message);
            return  "admin/housekeepCommand/housekeepCommandCreate";
        }
        
        try 
        {
        if (housekeepCommandService.find(housekeepCommand.getDescription()) != null ){
            message = "Fail to add new housekeep command due to duplicate entry for description";
            return "redirect:HousekeepCommandList?message="+message;
        }
            
            housekeepCommand.setLastUpdatedDate(new Date());
            housekeepCommand.setLastUpdatedBy(((UserStaffParticulars) session.getAttribute("userBean"))
                .getUserName());
            housekeepCommand.setEnableInd("Y".equals(housekeepCommand.getEnableInd())?"Y":"N");
            housekeepCommand.setCreateDate(new Date());
       
            housekeepCommandService.addHousekeepCommand(housekeepCommand);
            message = "Housekeep command has been add successfully.";
        }
        catch (Exception e) 
        {
           
            log.error("Fail to add new housekeep command." + e.getMessage());
             message = "Fail to add new housekeep command due to" + e.getMessage();
        }
        return "redirect:/admin/housekeepcommand/HousekeepCommandList?message="+message;
    }

    @DeleteMapping("/HousekeepCommand/{description}")
    public String delete(Model model, @PathVariable("description") String description) {
        String message = "";
        try
        {
            housekeepCommandService.delete(description);
            message = "Housekeep command has been deleted successfully.";
        } 
        catch (Exception e) 
        {
            
            log.error("Fail for find HousekeepCommand by description=" + //
                    (description == null ? "null" : description), e.getMessage());
            message = "Fail to delete housekeep command due to " + e.getMessage();
        }

        return "redirect:/admin/housekeepcommand/HousekeepCommandList?message="+message;
    }
}
