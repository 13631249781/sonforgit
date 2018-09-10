package com.hkt.rms.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserBean {
    @Id
    private String staffNum = "";
    private String encryptPassword = "";
    private String userName = "";
    private String exUserName = "";
    private String roleId = "";
    private String groupId = "";
    private String deptId = "";
    private String firstName = "";
    private String lastName = "";
    private String chineseName = "";
    private String jobTitle = "";
    private String grade = "";
    private String ccc = "";
    private String cccName = "";
    private String company = "";
    private String branch = "";
    private String msMailAddress = "";
    private String internetAddress = "";
    private String location = "";
    private String telephone = "";
    private String officePhone = "";
    private String officeFax = "";
    private String mobile = "";
    private String pager = "";
    private String responsibility = "";
    private String remark = "";
    private String manStaffNum = "";
    private String email = "";
    private String status = "";
    private String lastLoginDate = "";
    private String password = "";
    private String createdDate = "";
    private String question = "";
    private String answer = "";
    private String modifyDate = "";

    private String pwdUpdateDate = "";
    private int pwdExpiryDay = 0;
    private int pwdAge = 0;
    private String defaultPwd = "";
    private String reset = "";
    private String revokeDate = "";
    private int pwdTry = 0;
    private String roleName = "";
    private String addAction = "";
    private String deleteAction = "";
    private String updateAction = "";
    private String viewAction = "";
    private String groupName = "";

    // For CAS logout
    private boolean isCasLogin = false;
    
    public String getAllowAction()
    {
          /*  if (deleteAction.equals("Y") && updateAction.equals("Y"))
                    return "All";
            else if (addAction.equals("Y")) return "Add";*/

            return "View";
    }
}
