package com.example.splitwise.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.splitwise.dtos.GroupRequest;
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

    // CREATE Mapping
    public Group createGroup(GroupRequest request) {
        Group group = Group.builder()
                .name(request.getName())
                .description(request.getDescription())
                .members(userService.getUsers(request.getMembers()))
                .admins(userService.getUsers(request.getAdmins()))
                .build();

        return groupRepository.save(group);
    }

    // READ Mapping
    // READ by ID
    public Group getGroup(UUID groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    //READ All
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // UPDATE Mapping
    public Group updateGroup(GroupRequest request, UUID groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        Optional.ofNullable(request.getName()).ifPresent(group::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(group::setDescription);

        updateUsers(request.getAdmins(), group::setAdmins);
        updateUsers(request.getMembers(), group::setMembers);

        return groupRepository.save(group);
    }

    // UPDATE Helper
    private void updateUsers(Set<UUID> userIds, Consumer<Set<User>> setUsers) {
        if (userIds != null) {
            Set<User> users = userIds.stream()
                    .map(userService::getUser)
                    .collect(Collectors.toSet());
            setUsers.accept(users);
        }
    }

    // DELETE Mapping
    // DELETE by ID
    public void deleteGroup(UUID groupId) {
        groupRepository.deleteById(groupId);
    }
}
