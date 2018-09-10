package com.hkt.rms.dao.schedule;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.rms.dao.HousekeepCommandRepository;
import com.hkt.rms.service.HousekeepCommandService;

@Service
public class HousekeepingDao extends BaseDao{

    @Autowired
    private HousekeepCommandRepository housekeepCommandRepository;
    
    @Autowired
    private HousekeepCommandService housekeepCommandService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public final static String BATCH_JOB_ID = "HOUSEKEEP";
    
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public  void run() throws Exception
    {
            String sql = "";

            try
            {
                    ArrayList<String> list = housekeepCommandService.selectAllEnabledCommand();

                    for (int i = 0; i < list.size(); i++)
                    {
                            sql = list.get(i);
                            jdbcTemplate.execute(sql);
                    }

                    updateLastReferenceDate(jdbcTemplate, BATCH_JOB_ID);
            }
            catch (Exception e)
            {
                    updateError(jdbcTemplate, BATCH_JOB_ID, e.getMessage() + " [" + sql + "]");
                    throw e;
            }
    }
}
