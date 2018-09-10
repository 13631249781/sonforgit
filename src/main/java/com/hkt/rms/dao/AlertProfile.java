package com.hkt.rms.dao;

import com.hkt.rms.vo.UserAlertVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlertProfile {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<UserAlertVo> findAlertByUserName(String userName) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ua.user_name,cpd.app_cd,cpd.app_desc from comm_para_details cpd left join user_alert ua ");
        sql.append("on (cpd.app_cd =ua.alert_type and ua.user_name=:userName) ");
        sql.append("where cpd.master_app_cd in ('ALT','ERRALT')");
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        return namedParameterJdbcTemplate.query(sql.toString(), map, (resultSet, i) -> {
            UserAlertVo vo = new UserAlertVo();
            vo.setAlertId(resultSet.getString("app_cd"));
            vo.setAlertDescription(resultSet.getString("app_desc"));
            vo.setUserAvailability(!StringUtils.isEmpty(resultSet.getString("user_name")));
            return vo;
        });
    }

}
