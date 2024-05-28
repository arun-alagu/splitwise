package com.example.splitwise.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.splitwise.dtos.CreateGroupRequest;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.User;
import com.example.splitwise.repository.GroupRepository;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

    public GroupService(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    // POST Mapping
    public Group createGroup(CreateGroupRequest request) {
        Group group = Group.builder()
                .name(request.getName())
                .description(request.getDescription())
                .members(userService.getUsers(request.getMembers()))
                .admins(userService.getUsers(request.getAdmins()))
                .build();

        return groupRepository.save(group);
    }

    // GET Mapping
    public Group getGroup(UUID groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // PATCH Mapping
    public Group updateGroup(CreateGroupRequest request, UUID groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (request.getName() != null)
            group.setName(request.getName());
        if (request.getDescription() != null)
            group.setDescription(request.getDescription());

        if (request.getAdmins() != null) {
            List<User> admins = request.getAdmins().stream()
                    .map(adminId -> userService.getUser(adminId))
                    .collect(Collectors.toList());
            group.setAdmins(admins);
        }
        if (request.getMembers() != null) {
            List<User> members = request.getMembers().stream()
                    .map(memberId -> userService.getUser(memberId))
                    .collect(Collectors.toList());
            group.setMembers(members);
        }

        return groupRepository.save(group);
    }

    // DELETE Mapping
    public void deleteGroup(UUID groupId) {
        groupRepository.deleteById(groupId);
    }
}
