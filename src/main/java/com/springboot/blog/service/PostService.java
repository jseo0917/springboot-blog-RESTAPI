package com.springboot.blog.service;

import com.springboot.blog.Dto.PostDto;
import com.springboot.blog.Dto.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPost(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePost(Long id);
}
