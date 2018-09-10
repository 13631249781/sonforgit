package com.hkt.rms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hkt.rms.bean.admin.HousekeepCommand;
import com.hkt.rms.dao.HousekeepCommandRepository;

@Service
public class HousekeepCommandService {

    @Autowired
    private HousekeepCommandRepository housekeepCommandRepository;
    

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<HousekeepCommand> getHousekeepCommandList(){
        return housekeepCommandRepository.findByOrderBySeqNoAsc();
        
    }
    
    public HousekeepCommand addHousekeepCommand(HousekeepCommand housekeepCommand){
        
        return housekeepCommandRepository.save(housekeepCommand);
        
    }
    
    public HousekeepCommand find(String description){
        return housekeepCommandRepository.findByDescription(description);
    }
    
    public void delete(String description){
        HousekeepCommand entity = new HousekeepCommand();
        entity.setDescription(description);
        housekeepCommandRepository.delete(entity);
    }
    
    public HousekeepCommand update(HousekeepCommand housekeepCommand){
        return  housekeepCommandRepository.save(housekeepCommand);
        
    }
    
    public  ArrayList<String> selectAllEnabledCommand(){
        String sql = "SELECT SQL_COMMAND FROM HOUSEKEEP_COMMAND WHERE ENABLE_IND = 'Y' ORDER BY SEQ_NO";
        return  (ArrayList<String>) jdbcTemplate.queryForList(sql,String.class);
        
    }
}
