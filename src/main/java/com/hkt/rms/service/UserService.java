package com.hkt.rms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkt.rms.bean.UserStaffParticulars;
import com.hkt.rms.bean.admin.UserAlert;
import com.hkt.rms.dao.UserAlertRepository;
import com.hkt.rms.dao.UserDepartmentRepository;
import com.hkt.rms.dao.UserGroupRepository;
import com.hkt.rms.dao.UserRoleRepository;
import com.hkt.rms.dao.UserStaffRepository;
import com.hkt.rms.vo.UserStaffVo;


@Service
@Slf4j
public class UserService {
 /*   @Autowired
    JPAQueryFactory queryFactory;*/

    @Autowired
    UserStaffRepository userStaffRepository;

    @Autowired
    UserAlertRepository userAlertRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserDepartmentRepository userDepartmentRepository;

    @Transactional(rollbackFor = Exception.class)
    public UserStaffParticulars updateUserSimpleInfo(UserStaffVo modifyUserBean, UserStaffParticulars userBean) {
        UserStaffParticulars staff = userStaffRepository.getOne(userBean.getStaffNum());
        staff.setChineseName(modifyUserBean.getChineseName());
        staff.setFirstName(modifyUserBean.getFirstName());
        staff.setLastName(modifyUserBean.getLastName());
        staff.setLocation(modifyUserBean.getLocation());
        staff.setMobile(modifyUserBean.getMobile());
        staff.setTelephone(modifyUserBean.getTelephone());
        staff.setOfficeFax(modifyUserBean.getOfficeFax());
        staff.setMsMailAddress(modifyUserBean.getMsMailAddress());
        staff.setOfficePhone(modifyUserBean.getOfficePhone());
        userStaffRepository.save(staff);
        updateUserAlert(modifyUserBean, userBean);
        return staff;
    }

    private void updateUserAlert(UserStaffVo userStaffVo, UserStaffParticulars userStaffParticulars) {
        if (!userStaffVo.getAlertId().isEmpty()) {
            userAlertRepository.deleteByUserName(String.valueOf(userStaffParticulars.getStaffNum()));
            for (String s : userStaffVo.getAlertId()) {
                UserAlert ua = new UserAlert();
                ua.setAlertType(s);
                ua.setUserName(String.valueOf(userStaffParticulars.getStaffNum()));
                userAlertRepository.save(ua);
            }
        } else {
            userAlertRepository.deleteByUserName(String.valueOf(userStaffParticulars.getStaffNum()));
        }
    }


    public UserStaffParticulars getUserInfo(int userName) {
        return userStaffRepository.getOne(userName);
    }

    public List<UserStaffParticulars> getUserList() {
        return userStaffRepository.findAll();
    }

    public UserStaffParticulars findUserInfo(int staffNum) {
        return userStaffRepository.getOne(staffNum);
    }

    public Map<String, Object> checkAvailability(int staffNum, String userName, UserStaffParticulars userBean) {
        Map<String, Object> result = new HashMap<>();
        Optional<UserStaffParticulars> user = userStaffRepository.findByStaffNumAndUserName(staffNum, userName);
        if (user.isPresent()) {
            result.put("msg", "failed");
        } else {
            if (userBean.getPwdExpiryDay() == 99999) {
                // TODO: 2018/9/3  get user info via ldap

                // TODO: 2018/9/3  is Resigned
//                result.put("isResigned", "Y");
//                result.put("userInfo", user);
            }
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createUser(UserStaffVo userStaffVo) throws ParseException {
        Map<String, Object> result = new HashMap<>();
        Optional<UserStaffParticulars> user = userStaffRepository.findByStaffNumAndUserName(userStaffVo.getStaffNum(), userStaffVo.getUserName());
        if (user.isPresent()) {
            result.put("result", false);
            result.put("msg", "The staff number or user name already exists.");
        } else {
            if (isValidRevokeDate(userStaffVo.getRevokeDate(), result)) {
                UserStaffParticulars userInfo = new UserStaffParticulars();
                userInfo.setStaffNum(userStaffVo.getStaffNum());
                userInfo.setUserName(userStaffVo.getUserName());
                userInfo.setFirstName(userStaffVo.getFirstName());
                userInfo.setLastName(userStaffVo.getLastName());
                userInfo.setChineseName(userStaffVo.getChineseName());
                userInfo.setUserRole(userRoleRepository.getOne(Integer.valueOf(userStaffVo.getRole())));
                userInfo.setUserGroup(userGroupRepository.getOne(Integer.valueOf(userStaffVo.getGroup())));
                userInfo.setUserDepartment(userDepartmentRepository.getOne(userStaffVo.getDepartment()));
                userInfo.setJobTitle(userStaffVo.getJobTitle());
                userInfo.setGrade(userStaffVo.getGrade());
                userInfo.setBranch(userStaffVo.getBranch());
                userInfo.setCcc(userStaffVo.getCcc());
                userInfo.setCccName(userStaffVo.getCccName());
                userInfo.setCompany(userStaffVo.getCompany());
                userInfo.setMsMailAddress(userStaffVo.getMsMailAddress());
                userInfo.setEmail(userStaffVo.getEmail());
                userInfo.setInternetAddress(userStaffVo.getInternetAddress());
                userInfo.setLocation(userStaffVo.getLocation());
                userInfo.setTelephone(userStaffVo.getTelephone());
                userInfo.setOfficePhone(userStaffVo.getOfficePhone());
                userInfo.setOfficeFax(userStaffVo.getOfficeFax());
                userInfo.setMobile(userStaffVo.getMobile());
                userInfo.setPager(userStaffVo.getPager());
                userInfo.setStatus(userStaffVo.getStatus());
                userInfo.setUserExpiryDate(new SimpleDateFormat("yyyy/MM/dd").parse(userStaffVo.getRevokeDate()));
                userInfo.setResponsibility(userStaffVo.getResponsibility());
                userInfo.setManStaffNum(userStaffVo.getManStaffNum());
                userInfo.setRemark(userStaffVo.getRemark());
                userInfo.setModifyDate(new Date());
                userStaffRepository.save(userInfo);
                updateUserAlert(userStaffVo, userInfo);

                result.put("result", true);
                result.put("msg", "The user account has been created.");
            }
        }
        return result;
    }

    private boolean isValidRevokeDate(String revokeDate, Map<String, Object> result) {
        boolean flag = true;
        int day = Integer.parseInt(revokeDate.substring(8, 10));
        int month = Integer.parseInt(revokeDate.substring(5, 7));
        int year = Integer.parseInt(revokeDate.substring(0, 4));
        GregorianCalendar vDate = new GregorianCalendar(year, month - 1, day);
        GregorianCalendar today = new GregorianCalendar();
        if (!vDate.isLeapYear(year) && (month == 2 && day == 29)) {
            result.put("result", false);
            result.put("msg", "The given year is not a leap year. Please input an appropriate date.");
            flag = false;
        } else if (vDate.before(today)) {
            result.put("result", false);
            result.put("msg", "The revoke date must be after today.");
            flag = false;
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> editUser(UserStaffVo userStaffVo) throws ParseException {
        Map<String, Object> result = new HashMap<>();
        UserStaffParticulars userInfo = userStaffRepository.getOne(userStaffVo.getStaffNum());
        userInfo.setStaffNum(userStaffVo.getStaffNum());
        userInfo.setUserName(userStaffVo.getUserName());
        userInfo.setFirstName(userStaffVo.getFirstName());
        userInfo.setLastName(userStaffVo.getLastName());
        userInfo.setChineseName(userStaffVo.getChineseName());
        userInfo.setUserRole(userRoleRepository.getOne(Integer.valueOf(userStaffVo.getRole())));
        userInfo.setUserGroup(userGroupRepository.getOne(Integer.valueOf(userStaffVo.getGroup())));
        userInfo.setUserDepartment(userDepartmentRepository.getOne(userStaffVo.getDepartment()));
        userInfo.setJobTitle(userStaffVo.getJobTitle());
        userInfo.setGrade(userStaffVo.getGrade());
        userInfo.setBranch(userStaffVo.getBranch());
        userInfo.setCcc(userStaffVo.getCcc());
        userInfo.setCccName(userStaffVo.getCccName());
        userInfo.setCompany(userStaffVo.getCompany());
        userInfo.setMsMailAddress(userStaffVo.getMsMailAddress());
        userInfo.setEmail(userStaffVo.getEmail());
        userInfo.setInternetAddress(userStaffVo.getInternetAddress());
        userInfo.setLocation(userStaffVo.getLocation());
        userInfo.setTelephone(userStaffVo.getTelephone());
        userInfo.setOfficePhone(userStaffVo.getOfficePhone());
        userInfo.setOfficeFax(userStaffVo.getOfficeFax());
        userInfo.setMobile(userStaffVo.getMobile());
        userInfo.setPager(userStaffVo.getPager());
        userInfo.setStatus(userStaffVo.getStatus());
        userInfo.setUserExpiryDate(new SimpleDateFormat("yyyy/MM/dd").parse(userStaffVo.getRevokeDate()));
        userInfo.setResponsibility(userStaffVo.getResponsibility());
        userInfo.setManStaffNum(userStaffVo.getManStaffNum());
        userInfo.setRemark(userStaffVo.getRemark());
        userInfo.setModifyDate(new Date());
        userStaffRepository.save(userInfo);
        updateUserAlert(userStaffVo,userInfo);
        result.put("msg", "The user information is updated.");
        result.put("result", true);
        return result;
    }

    public Map<String, Object> deleteUser(int staffNum) {
        Map<String, Object> result = new HashMap<>();
        UserStaffParticulars userInfo=userStaffRepository.getOne(staffNum);
        userInfo.setStatus("Deleted");
        userStaffRepository.save(userInfo);
        result.put("msg", "The account " + staffNum + " is deleted successfully.");
        result.put("result", true);
        return result;
    }
}












