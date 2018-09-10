package com.hkt.rms.service;

import com.hkt.rms.bean.admin.UserGroup;
import com.hkt.rms.dao.UserGroupRepository;
import com.hkt.rms.dao.UserStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupService {

    @Autowired
    UserStaffRepository userStaffRepository;
    @Autowired
    UserGroupRepository groupRepository;

    public Map<String, Object> updateGroupInfo(UserGroup userGroup) {
        Map<String, Object> result = new HashMap<>();
        UserGroup groupInfo = groupRepository.getOne(userGroup.getGroupId());
        if (groupRepository.findByGroupName(userGroup.getGroupName()).isPresent()) {
            result.put("type", "orange");
            result.put("msg", "The group name is duplicated.");
        } else {
            groupInfo.setDescription(userGroup.getDescription());
            groupInfo.setGroupName(userGroup.getGroupName());
            groupRepository.save(groupInfo);
            result.put("type", "green");
            result.put("msg", "The group " + userGroup.getGroupName() + " is updated successfully.");
        }
        return result;
    }

    public Map<String, Object> deleteGroup(int groupId) {
        Map<String, Object> result = new HashMap<>();
        if (!userStaffRepository.findByUserGroup(groupRepository.getOne(groupId)).isEmpty()) {
            result.put("msg", "The group is related to some of users.<br>Please make sure no relation between group and users before deletion.");
            result.put("type", "orange");
        } else {
            groupRepository.deleteById(groupId);
            result.put("msg", "The group ID " + groupId + " is deleted successfully.");
            result.put("type", "green");
        }
        return result;
    }

    public Map<String, Object> createGroup(UserGroup userGroup) {
        Map<String, Object> result = new HashMap<>();
        if (groupRepository.findByGroupName(userGroup.getGroupName()).isPresent()) {
            result.put("type", "orange");
            result.put("msg", "The group name already exists.");
        } else {
            groupRepository.save(userGroup);
            result.put("type", "green");
            result.put("msg", "The group is created successfully.");
        }
        return result;
    }

    //======================================================

    @Autowired JdbcTemplate jdbcTemplate;

    private final static String SELECTGROUP = "SELECT group_id,description,group_name FROM user_group";

    public List<UserGroup> getList(String type) {
        String sql = "";
        if ("ADD".equals(type)) {
            sql = SELECTGROUP+" WHERE group_id NOT IN (SELECT group_id FROM user_group_menu) ORDER BY group_name";
        }else {
            sql = SELECTGROUP+" WHERE group_id IN (SELECT group_id FROM user_group_menu) ORDER BY group_name";
        }
        List<UserGroup> groups = jdbcTemplate.query(sql,new BeanPropertyRowMapper(UserGroup.class));

        if (groups != null && groups.size() > 0) {
            return groups;
        }else {
            return null;
        }
    }

    public UserGroup findGroupByGroupId(Integer groupId) {
        String sql =  SELECTGROUP +" WHERE group_id = ?";
        List<UserGroup> groups = jdbcTemplate.query(sql,new Object[]{groupId},new BeanPropertyRowMapper<>(UserGroup.class));
        if (groups != null && groups.size() > 0){
            return groups.get(0);
        }else {
            return null;
        }
    }
}
