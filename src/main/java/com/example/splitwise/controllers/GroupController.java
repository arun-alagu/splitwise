package com.example.splitwise.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.splitwise.dtos.CreateGroupRequest;
import com.example.splitwise.dtos.GetGroupResponse;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.services.GroupService;

@RestController
@RequestMapping(path = "group/")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(path = "/{id}")
    @ResponseBody public GetGroupResponse getGroup(@PathVariable(name = "id") UUID groupId){
        Group group = groupService.getGroup(groupId);

        List<UUID> members = new ArrayList<>();
        for(User user : group.getMembers()){
            members.add(user.getId());
        }

        List<UUID> admins = new ArrayList<>();
        for (User user : group.getAdmins()) {
            admins.add(user.getId());
        }

        GetGroupResponse response = GetGroupResponse.builder()
        .id(group.getId())
        .name(group.getName())
        .description(group.getDescription())
        .members(members)
        .admins(admins)
        .createdBy(group.getCreatedBy() != null ? group.getCreatedBy().getId() : null)
        .build();

        return response;
    }

    @GetMapping(path = "/all")
    @ResponseBody public List<GetGroupResponse> getAllGroups(){
        List<GetGroupResponse> response = new ArrayList<>();
        for(Group group : groupService.getAllGroups()){
            response.add(getGroup(group.getId()));
        }
        return response;
    }

    @PostMapping(path = "/create")
    @ResponseBody public UUID createGroup(@RequestBody CreateGroupRequest request){
        return groupService.createGroup(request).getId();
    }

    @PatchMapping(path = "/{id}")
    @ResponseBody public UUID updateGroup(@RequestBody CreateGroupRequest request, 
    @PathVariable(name = "id") UUID groupId){
        return groupService.updateGroup(request, groupId).getId();
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody public void deleteGroup(@PathVariable(name = "id") UUID groupId){
        groupService.deleteGroup(groupId);
    }
}
