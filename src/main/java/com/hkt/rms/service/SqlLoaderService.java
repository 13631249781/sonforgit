package com.hkt.rms.service;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hkt.rms.dao.SQLLoader;

@Service
@Slf4j
public class SqlLoaderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ArrayList<String> processSql(String sql) throws SQLException {
        return SQLLoader.processSQLs(jdbcTemplate, sql);
    }
    
    
    public void createExcelFile(String []sql,HttpServletResponse response) throws Exception{
        SQLLoader.createExcelFile(jdbcTemplate, response, sql);
    }
}
