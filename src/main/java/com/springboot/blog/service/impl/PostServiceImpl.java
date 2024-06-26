package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.Dto.PostDto;
import com.springboot.blog.Dto.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exceptionHandler.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{

    private PostRepository postRepository;
    private ModelMapper mapper;    

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert DTO to entity
        Post post = mapToEntity(postDto);

        // Save post entity into the MySQL database
        Post newPost = postRepository.save(post);

        // Return the saved details to REST API
        // convert entity to DTO        
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
        // Pagable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listofPosts = posts.getContent();
        
        List<PostDto> content = listofPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));        
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));                
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        postRepository.save(post);
        return mapToDTO(post);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));                
        postRepository.delete(post);        
    }
 
    private PostDto mapToDTO(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);        
        return post;
    }
}
