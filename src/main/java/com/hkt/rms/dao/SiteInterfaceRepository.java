package com.hkt.rms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hkt.rms.bean.admin.SiteInterfaceBean;

public interface SiteInterfaceRepository extends JpaRepository<SiteInterfaceBean,String>{

    
      List<SiteInterfaceBean> findByOrderBySystemName();
      
      SiteInterfaceBean findBySystemName(String systemName); 
      
}
