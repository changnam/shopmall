package com.honsoft.shopmall.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.RoleDto;
import com.honsoft.shopmall.entity.Role;
import com.honsoft.shopmall.mapper.RoleMapper;
import com.honsoft.shopmall.repository.RoleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id+ " not found"));
        return roleMapper.toDto(role);
    }

    @Override
    public RoleDto updateRole(String id, RoleDto roleDto) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found: " + id));
        
        // MapStruct @AfterMapping will handle permissionIds
        Role forUpdateRole = roleMapper.toEntity(roleDto); // update 할 role 에 permissions 정보가 모드 로드되어 있다.
       
        existingRole.getRolePermissions().clear(); // permission 클리어 여부를 판단할것 . 기존 permission 을 삭제할것인지 
        
        existingRole.setName(roleDto.getName());
        existingRole.setRolePermissions(forUpdateRole.getRolePermissions()); //새로운 permissions 셋팅
        
        Role updatedRole = roleRepository.save(existingRole);
        return roleMapper.toDto(updatedRole);
    }

    @Override
    public void deleteRoleById(String id) {
        roleRepository.deleteById(id);
    }
}

