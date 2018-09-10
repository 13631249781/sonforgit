package com.hkt.rms.utils;

import java.util.Date;

public class SqlUtil {

    public static boolean isEmptyValue(String value)
    {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isNotEmptyValue(String value)
    {
        return value != null && !"".equals(value.trim());
    }


    public static String appendWhereCause(String whereCause, String expression, int value)
    {
        return appendWhereCause(whereCause, expression, value > 0);
    }

    public static String appendWhereCause(String whereCause, String expression, long value)
    {
        return appendWhereCause(whereCause, expression, value > 0);
    }

    public static String appendWhereCause(String whereCause, String expression, Date value)
    {
        return appendWhereCause(whereCause, expression, value != null);
    }

    public static String appendWhereCause(String whereCause, String expression, String value)
    {
        return appendWhereCause(whereCause, expression, !isEmptyValue(value));
    }

    public static String appendWhereCause(String whereCause, String expression, boolean isAppend)
    {
        if (isAppend)
        {
            if (whereCause.length() > 0) whereCause += " AND ";
            whereCause += expression;
        }

        return whereCause;
    }

    public static String appendWhereCauseWithOr(String whereCause, String expression, String value)
    {
        if (isNotEmptyValue(value))
        {
            if (whereCause.length() > 0) whereCause += " OR ";
            whereCause += expression;
        }

        return whereCause;
    }

    public static String getCriteria(String errorType, String orderNo, int status) {
        String criteria = " WHERE cpd.master_app_cd = 'ERR'";
        criteria = appendWhereCause(criteria,"ERROR_TYPE='" + errorType + "'", errorType);
        criteria = appendWhereCause(criteria, "ORDER_NO LIKE '" + orderNo + "%'", orderNo);
        //criteria = appendWhereCause(criteria, "SERVICE_NO LIKE '" + serviceNo + "%'", serviceNo);
        if (criteria.length() > 0) criteria += " AND ";
        System.out.println(status);
        criteria += status != 0 ? "STATUS=" + status : "STATUS<3";
        return criteria;
    }
}
