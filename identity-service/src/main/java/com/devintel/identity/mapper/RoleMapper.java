package com.devintel.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.devintel.identity.dto.request.RoleRequest;
import com.devintel.identity.dto.response.RoleResponse;
import com.devintel.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
