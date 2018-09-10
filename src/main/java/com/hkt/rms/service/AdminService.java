package com.hkt.rms.service;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.bean.admin.CommParaDetails;
import com.hkt.rms.dao.CommParaDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AdminService {

    @Autowired
    CommParaDetailsRepository commParaDetailsRepository;

    public String updateChildCode(CommParaDetails modifyCode) {
        CommParaDetails commParaDetails=commParaDetailsRepository.getOne(modifyCode.getId());
        commParaDetails.setAppDesc(modifyCode.getAppDesc());
        commParaDetailsRepository.save(commParaDetails);
        return "The child code is updated successfully.";
    }

    public Map<String, Object> createChildCode(CommParaDetails newChildCode, UserStaffParticulars userBean) {
        Map<String, Object> result = new HashMap<>();
        if (commParaDetailsRepository.findByAppCdAndMasterAppCd(newChildCode.getAppCd(), newChildCode.getMasterAppCd()).isPresent()) {
            result.put("type", "orange");
            result.put("msg", "Chile Code "+newChildCode.getAppCd()+" already exists.");
        }else{
            newChildCode.setLastUpdId(String.valueOf(userBean.getStaffNum()));
            newChildCode.setCreateDate(new Date());
            newChildCode.setLastUpdDate(new Date());
            commParaDetailsRepository.save(newChildCode);
            result.put("type", "green");
            result.put("msg", "The child code is created successfully.");
        }
        return result;
    }

    public Map<String, Object> deleteChildCode(String childCodeId) {
        Map<String, Object> result = new HashMap<>();
        commParaDetailsRepository.deleteById(Long.valueOf(childCodeId));
        result.put("type", "green");
        result.put("msg", "The child code is deleted successfully.");
        return result;
    }
}
