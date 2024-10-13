package com.devintel.post.service;

import com.devintel.post.dto.request.PostRequest;
import com.devintel.post.dto.response.PostResponse;
import com.devintel.post.entity.Post;
import com.devintel.post.mapper.PostMapper;
import com.devintel.post.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    PostMapper postMapper;

    public PostResponse createPost(PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Post post = Post.builder()
                .content(request.getContent())
                .userId(authentication.getName())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .build();
        post = postRepository.save(post);

        return postMapper.toPostResponse(post);
    }

    public List<PostResponse> getMyPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

       List<Post> posts = postRepository.findAllByUserId(userId);
       return posts.stream().map(postMapper::toPostResponse).toList();
    }
}
