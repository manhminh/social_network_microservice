package com.devintel.profile.mapper;

import com.devintel.profile.dto.request.UserProfileCreationRequest;
import com.devintel.profile.dto.response.UserProfileResponse;
import com.devintel.profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
