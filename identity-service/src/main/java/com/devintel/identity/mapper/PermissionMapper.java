package com.devintel.identity.mapper;

import org.mapstruct.Mapper;

import com.devintel.identity.dto.request.PermissionRequest;
import com.devintel.identity.dto.response.PermissionResponse;
import com.devintel.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
