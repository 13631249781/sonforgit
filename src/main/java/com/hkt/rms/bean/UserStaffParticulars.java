package com.hkt.rms.bean;

import com.hkt.rms.bean.admin.UserDepartment;
import com.hkt.rms.bean.admin.UserGroup;
import com.hkt.rms.bean.admin.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class UserStaffParticulars {
    @Id
    @Column(length = 10)
    private int staffNum;
    @Column(length = 10)
    private String userName = "";
    //    @Column(length = 8)
//    private String roleId = "";
    @Column(length = 250)
    private String question = "";
    @Column(length = 250)
    private String answer = "";
    @Column(length = 50)
    private String firstName = "";
    @Column(length = 40)
    private String lastName = "";
    @Column(length = 30)
    private String chineseName = "";
    @Column(length = 60)
    private String jobTitle = "";
    @Column(length = 10)
    private String grade = "";
    @Column(length = 7)
    private String ccc = "";
    @Column(length = 60)
    private String cccName = "";
    @Column(length = 40)
    private String company = "";
    @Column(length = 55)
    private String branch = "";
    @Column(length = 50)
    private String msMailAddress = "";
    @Column(length = 40)
    private String internetAddress = "";
    @Column(length = 30)
    private String location = "";
    @Column(length = 20)
    private String telephone = "";
    @Column(length = 20)
    private String officePhone = "";
    @Column(length = 20)
    private String officeFax = "";
    @Column(length = 20)
    private String mobile = "";
    @Column(length = 20)
    private String pager = "";
    @Column(length = 250)
    private String responsibility = "";
    @Column(length = 250)
    private String remark = "";
    @Column(length = 10)
    private String manStaffNum = "";
    @Column(length = 200)
    private String email = "";
    @Column(length = 30)
    private String status = "";
    private Date createdDate =new Date();
    private Date lastLoginDate;
    private Date modifyDate;
    //    @Column(length = 8)
//    private String groupId = "";
    private Date pwdUpdateDate;
    @Column(length = 20)
    private String defaultPwd = "";
    @Column(length = 20)
    private String workingPwd = "";
    @Column(length = 2)
    private String reset = "Y";
    private Date userExpiryDate;
    //    @Column(length = 20)
//    private String deptId = "";
    @Column(length = 2)
    private int pwdTry = 0;
    
 
    @OneToOne
    @JoinColumn(name = "role_id")
    private UserRole userRole;

    @OneToOne
    @JoinColumn(name = "group_id")
    private UserGroup userGroup;

    @OneToOne
    @JoinColumn(name = "dept_id")
    private UserDepartment userDepartment;

    @Transient
    private int pwdExpiryDay = 0;
//    private String revokeDate = "";
//    private String roleName = "";
//    private String addAction = "";
//    private String deleteAction = "";
//    private String updateAction = "";
//    private String viewAction = "";
//    private String groupName = "";
//    private String password = "";
//
//    // For CAS logout
//    private boolean isCasLogin = false;
//
//    private int pwdAge = 0;
//    private String exUserName = "";
//    private String encryptPassword = "";

   
    
}
