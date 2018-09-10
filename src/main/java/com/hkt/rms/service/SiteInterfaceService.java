package com.hkt.rms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkt.rms.bean.admin.SiteInterfaceBean;
import com.hkt.rms.dao.SiteInterfaceRepository;

@Service
public class SiteInterfaceService {
    
    @Autowired
    private SiteInterfaceRepository siteInterfaceRepository;
    
    public List<SiteInterfaceBean> findAll(){
            return siteInterfaceRepository.findByOrderBySystemName();
    }
    
    public SiteInterfaceBean find(String systemName){
        return siteInterfaceRepository.findBySystemName(systemName);
    }

    public void update(SiteInterfaceBean siteInterfaceBean){
        
          siteInterfaceRepository.save(siteInterfaceBean);
    }
}
