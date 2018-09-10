package com.hkt.rms.controller;

import com.hkt.rms.bean.admin.UserGroup;
import com.hkt.rms.dao.UserGroupRepository;
import com.hkt.rms.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    UserGroupRepository groupRepository;

    @PostMapping("create")
    @ResponseBody
    public Map<String, Object> createGroup(UserGroup userGroup) {
        return groupService.createGroup(userGroup);
    }

    @GetMapping("viewGroupCreate")
    public String viewGroupCreate() {
        return "/admin/group/viewGroupCreate";
    }

    @GetMapping("viewGroupList")
    public String viewGroupList(Model model){
        model.addAttribute("groupList", groupRepository.findAll());
        return "/admin/group/viewGroupList";
    }

    @GetMapping("view/{groupId}")
    public String viewGroupInfo(@PathVariable int groupId, Model model){
        model.addAttribute("groupInfo", groupRepository.getOne(groupId));
        return "/admin/group/viewGroupInfo";
    }

    @GetMapping("update/{groupId}")
    public String viewGroupUpdate(@PathVariable int groupId,Model model) {
        model.addAttribute("groupInfo", groupRepository.getOne(groupId));
        return "/admin/viewGroupUpdate";
    }

    @PostMapping("update")
    @ResponseBody
    public Map<String,Object> updateGroupInfo(UserGroup userGroup) {
        return groupService.updateGroupInfo(userGroup);
    }

    @GetMapping("delete/{groupId}")
    @ResponseBody
    public Map<String, Object> deleteGroup(@PathVariable int groupId) {
        return groupService.deleteGroup(groupId);
    }
}
