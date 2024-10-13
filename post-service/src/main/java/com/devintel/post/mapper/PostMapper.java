package com.devintel.post.mapper;

import com.devintel.post.dto.response.PostResponse;
import com.devintel.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
