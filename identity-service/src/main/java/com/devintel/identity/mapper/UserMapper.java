package com.devintel.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.devintel.identity.dto.request.UserCreationRequest;
import com.devintel.identity.dto.request.UserUpdateRequest;
import com.devintel.identity.dto.response.UserResponse;
import com.devintel.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
