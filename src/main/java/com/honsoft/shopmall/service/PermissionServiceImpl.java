package com.honsoft.shopmall.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.honsoft.shopmall.dto.PermissionDto;
import com.honsoft.shopmall.entity.Permission;
import com.honsoft.shopmall.mapper.PermissionMapper;
import com.honsoft.shopmall.repository.PermissionRepository;

@Service
public class PermissionServiceImpl implements PermissionService{
	private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
	
	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;
	
	public  PermissionServiceImpl(PermissionRepository permissionRepository,PermissionMapper permissionMapper) {
		this.permissionRepository = permissionRepository;
		this.permissionMapper = permissionMapper;
	}
	
	@Override
	public PermissionDto createPermission(PermissionDto permissionDto) {
		Permission permission = permissionMapper.toEntity(permissionDto);
		Permission saved = permissionRepository.save(permission);
		PermissionDto savedDto = permissionMapper.toDto(saved);
		return savedDto;
	}

	@Override
	public PermissionDto getPermissionById(String permissionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PermissionDto> getAllPermissions() {
		List<Permission> permissions = permissionRepository.findAll();
		List<PermissionDto> dtos = permissionMapper.toDtoList(permissions);
		
		return dtos;
	}

	@Override
	public PermissionDto updatePermission(String permissionId, PermissionDto permissionDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePermissionById(String permissionId) {
		permissionRepository.deleteById(permissionId);
		
	}

}
