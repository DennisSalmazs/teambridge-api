package com.teambridge.service;

import com.teambridge.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();
    RoleDTO findById(Long id);
    RoleDTO findByDescription(String description);

}
