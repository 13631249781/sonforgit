package com.hkt.rms.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserStaffVo {
    private String firstName;
    private String lastName;
    private String chineseName;
    private String msMailAddress;
    private String location;
    private String officePhone;
    private String mobile;
    private String telephone;
    private String officeFax;
    private List<String> alertId = new ArrayList<>();

    //for user list
    private int staffNum;
    private String userName;
    private String department;
    private String role;
    private String group;
    private String jobTitle;
    private String status;

    //for user create view
    private String grade;
    private String branch;
    private String ccc;
    private String cccName;
    private String company;
    private String email;
    private String internetAddress;
    private String pager;
    private String revokeDate;
    private String responsibility;
    private String manStaffNum;
    private String remark;


}
