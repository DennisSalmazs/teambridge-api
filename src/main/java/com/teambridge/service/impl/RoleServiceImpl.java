package com.teambridge.service.impl;

import com.teambridge.dto.RoleDTO;
import com.teambridge.entity.Role;
import com.teambridge.mapper.MapperUtil;
import com.teambridge.repository.RoleRepository;
import com.teambridge.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDTO> listAllRoles() {

        //ask repository layer to give us list of roles from DB
        List<Role> roleList = roleRepository.findAll();

        return roleList.stream().
                map(role -> mapperUtil.convert(role, RoleDTO.class)).
                collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
        Role role = roleRepository.findById(id).get();
        return mapperUtil.convert(role, RoleDTO.class);
    }
}
