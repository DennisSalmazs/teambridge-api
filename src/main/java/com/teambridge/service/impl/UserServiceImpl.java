package com.teambridge.service.impl;

import com.teambridge.dto.ProjectDTO;
import com.teambridge.dto.TaskDTO;
import com.teambridge.dto.UserDTO;
import com.teambridge.entity.Role;
import com.teambridge.entity.User;
import com.teambridge.mapper.MapperUtil;
import com.teambridge.repository.UserRepository;
import com.teambridge.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final RoleService roleService;
    private final KeycloakService keycloakService;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, @Lazy ProjectService projectService, @Lazy TaskService taskService, RoleService roleService, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.projectService = projectService;
        this.taskService = taskService;
        this.roleService = roleService;
        this.keycloakService = keycloakService;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);
        return userList.stream().
                map(user -> mapperUtil.convert(user, UserDTO.class)).
                collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUserNameAndIsDeleted(username, false);
        return mapperUtil.convert(user, UserDTO.class);
    }

    @Override
    public void save(UserDTO user) {
        user.setRole(roleService.findByDescription(user.getRole().getDescription()));

//        keycloakService.userCreate(user);
        userRepository.save(mapperUtil.convert(user, User.class));
    }

    @Override
    public void update(String username, UserDTO user) {
        User foundUser = userRepository.findByUserNameAndIsDeleted(username, false); // has iD
        User updatedUser = mapperUtil.convert(user, User.class);
        updatedUser.setId(foundUser.getId());
        updatedUser.setUserName(username);

        Role role = mapperUtil.convert(roleService.findByDescription(user.getRole().getDescription()), Role.class);
        updatedUser.setRole(role);

        userRepository.save(updatedUser);
    }

    @Override
    public void delete(String username) {
        User user = userRepository.findByUserNameAndIsDeleted(username, false);

        //check if user can be deleted
        if (checkIfUserCanBeDeleted(mapperUtil.convert(user, UserDTO.class))){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId()); // so that creating user with same username is possible
            userRepository.save(user); // save, to update object in DB
        }
    }

    /**
     * Check if Manager has uncompleted projects,
     * or project has uncompleted tasks
     * @param user
     * @return
     */
    private boolean checkIfUserCanBeDeleted(UserDTO user){

        switch (user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(user);
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(user);
                return taskDTOList.size() == 0;
            default:
                return true;
        }
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role, false);
        return users.stream().
                map(user -> mapperUtil.convert(user, UserDTO.class)).
                collect(Collectors.toList());
    }
}
