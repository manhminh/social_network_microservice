package com.devintel.identity.mapper;

import com.devintel.identity.dto.request.UserCreationRequest;
import com.devintel.identity.dto.request.UserProfileCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileCreationRequest toUserProfileCreationRequest(UserCreationRequest request);
}
