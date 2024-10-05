package com.vn.identity_service.service;

import com.vn.identity_service.dto.request.PermissionRequest;
import com.vn.identity_service.dto.response.PermissionResponse;
import com.vn.identity_service.entity.Permission;
import com.vn.identity_service.mapper.PermisionMapper;
import com.vn.identity_service.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Permissions;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermisionMapper permisionMapper;
    public PermissionResponse create(PermissionRequest request) {
        Permission permissions = permisionMapper.toPermissions(request);
        permissions = permissionRepository.save(permissions);
        return permisionMapper.toPermissionResponse(permissions);
    }

    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
       return permissions.stream().map(permisionMapper:: toPermissionResponse).toList();
    }

    public void delete(String permissionId) {
        permissionRepository.deleteById(permissionId);
    }
}
