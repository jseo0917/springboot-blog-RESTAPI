package com.springboot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.Dto.PostDto;
import com.springboot.blog.Dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/posts")
public class PostController {
    
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    // Create blog post REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);        
    }

    // Get all posts REST API
    @GetMapping
    public PostResponse getAllPosts(
        @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEAFULT_PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstants.DEAFULT_SORT_DIR, required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getMethodName(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {        
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);

        return new ResponseEntity<>("id: " + id + " post entity successfully deleted.", HttpStatus.OK);
        
    }
}
