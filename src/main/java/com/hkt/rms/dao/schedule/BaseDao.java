package com.hkt.rms.dao.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class BaseDao {
    //TODO un test
   
    public static  int updateError(JdbcTemplate jdbcTemplate,String batchJobId, String lastError)
    {
         
            if (lastError != null && lastError.length() > 500) lastError = lastError.substring(0, 500);

            try{
            String sql = "UPDATE BATCH_JOB SET LAST_RAN_DATE = SYSDATE(), LAST_ERROR = ? WHERE BATCH_JOB_ID = ?";
               
            jdbcTemplate.update(sql,new Object[]{lastError,batchJobId});
            }
            catch (Exception sqle)
            {
                    sqle.printStackTrace();
            }
            return 0;
    }
    
    protected static int updateLastReferenceDate(JdbcTemplate jdbcTemplate, String batchJobId)
    {
            return updateLastReferenceDate(jdbcTemplate, batchJobId, new Date());
    }
    
    
    //TODO un test
    public static int updateLastReferenceDate(JdbcTemplate jdbcTemplate, String batchJobId, Date lastReferenceDate)
    {

            try
            {      String sql = "UPDATE BATCH_JOB SET LAST_REFERENCE_DATE = ?, LAST_RAN_DATE = SYSDATE(), LAST_ERROR = NULL WHERE BATCH_JOB_ID = ?";
                    return jdbcTemplate.update(sql, new Object[]{lastReferenceDate,batchJobId});
            }
            catch (Exception sqle)
            {
                    sqle.printStackTrace();
            }
            return 0;
    }

   
}
