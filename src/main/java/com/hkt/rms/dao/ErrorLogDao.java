package com.hkt.rms.dao;

import com.hkt.rms.bean.error.ErrorLog;
import com.hkt.rms.utils.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ErrorLogDao {

    @Autowired NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<ErrorLog> getErrorLogList(String errorType,String orderNo,int status) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT el.log_id,cpd.app_desc as errorTypeDesc,el.service_no,el.order_no,el.status,el.create_date,el.last_updated_date");
        sql.append(",el.last_updated_by,el.description");
        sql.append(" FROM error_log el INNER JOIN comm_para_details cpd ON el.error_type = cpd.app_cd");
        sql.append(SqlUtil.getCriteria(errorType,orderNo,status));
        sql.append(" ORDER BY el.create_date");
        System.out.println(sql.toString());
        List<ErrorLog> errorLogs = namedParameterJdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper<>(ErrorLog.class));
        if (errorLogs != null && errorLogs.size() > 0){
            return errorLogs;
        }else {
            return  null;
        }
    }
}
