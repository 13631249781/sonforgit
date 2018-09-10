package com.hkt.rms.service;

import com.hkt.rms.bean.error.ErrorLog;
import com.hkt.rms.dao.ErrorLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErrorLogService {

    @Autowired ErrorLogDao errorLogDao;

    public List<ErrorLog> findAllByPage (String errorType,String orderNo,int status){
        if ("0".equals(errorType)){
            errorType = "";
        }
        return errorLogDao.getErrorLogList(errorType,orderNo,status);
    }
}
