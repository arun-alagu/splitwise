package com.example.splitwise.controllers;

import java.util.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.splitwise.dtos.GroupRequest;
import com.example.splitwise.dtos.GroupResponse;
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
    @ResponseBody public GroupResponse getGroup(@PathVariable(name = "id") UUID groupId){
        Group group = groupService.getGroup(groupId);

        Set<UUID> members = new HashSet<>();
        for(User user : group.getMembers()){
            members.add(user.getId());
        }

        Set<UUID> admins = new HashSet<>();
        for (User user : group.getAdmins()) {
            admins.add(user.getId());
        }

        GroupResponse response = GroupResponse.builder()
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
    @ResponseBody public List<GroupResponse> getAllGroups(){
        List<GroupResponse> response = new ArrayList<>();
        for(Group group : groupService.getAllGroups()){
            response.add(getGroup(group.getId()));
        }
        return response;
    }

    @PostMapping(path = "/create")
    @ResponseBody public UUID createGroup(@RequestBody GroupRequest request){
        return groupService.createGroup(request).getId();
    }

    @PatchMapping(path = "/{id}")
    @ResponseBody public UUID updateGroup(@RequestBody GroupRequest request,
    @PathVariable(name = "id") UUID groupId){
        return groupService.updateGroup(request, groupId).getId();
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody public void deleteGroup(@PathVariable(name = "id") UUID groupId){
        groupService.deleteGroup(groupId);
    }
}
