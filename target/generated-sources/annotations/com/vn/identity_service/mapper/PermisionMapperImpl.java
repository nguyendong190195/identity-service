package com.vn.identity_service.mapper;

import com.vn.identity_service.dto.request.PermissionRequest;
import com.vn.identity_service.dto.response.PermissionResponse;
import com.vn.identity_service.entity.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class PermisionMapperImpl implements PermisionMapper {

    @Override
    public Permission toPermissions(PermissionRequest permission) {
        if ( permission == null ) {
            return null;
        }

        Permission.PermissionBuilder permission1 = Permission.builder();

        permission1.name( permission.getName() );
        permission1.description( permission.getDescription() );

        return permission1.build();
    }

    @Override
    public PermissionResponse toPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse permissionResponse = new PermissionResponse();

        return permissionResponse;
    }
}
