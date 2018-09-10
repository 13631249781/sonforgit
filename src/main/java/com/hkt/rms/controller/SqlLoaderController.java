package com.hkt.rms.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hkt.rms.service.SqlLoaderService;
import com.hkt.rms.utils.UtilBase;

@Controller
@RequestMapping("/admin/sqlloader")
@Slf4j
public class SqlLoaderController {

    @Autowired
    private SqlLoaderService sqlLoaderService;
    
    @GetMapping("/SqlLoader")
    public String ToSqlLoaderPage(){
        
        return "admin/sqlloader/SqlLoader";
    }
    
    @PostMapping("/SqlLoader")
    public String processSQL(Model model,String sql,String act,HttpServletResponse response){
        try
        {
            if(!UtilBase.isEmptyValue(sql)){
               
                if(act.equals("exportToExcel")){
                    sqlLoaderService.createExcelFile(sql.split(";"), response);
                    return null;
                }
                
               ArrayList<String> resultList = sqlLoaderService.processSql(sql);
               String successMessage = "SQL script has been executed successfully.";
               model.addAttribute("resultList", resultList);
               model.addAttribute("successMessage", successMessage);
              
            }
        }
        catch(Exception e)
        {
            log.error("Fail for SqlLoader due to", e.getMessage());
            String errorMessage = "Fail for SqlLoader due to" + e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
        }
        
        model.addAttribute("sql", sql);
        return "admin/sqlloader/SqlLoader";
    }
    

}
