package com.vn.identity_service.mapper;

import com.vn.identity_service.dto.request.PermissionRequest;
import com.vn.identity_service.dto.response.PermissionResponse;
import com.vn.identity_service.entity.Permission;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PermisionMapper {
    Permission toPermissions(PermissionRequest permission);
    PermissionResponse toPermissionResponse(Permission permission);
}
