package com.springboot.blog.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {        
    private Long id;    
    /*
     * Title
     * Should not be empty
     * Should contain at least 2 characters
     */    
    @NotEmpty
    @Size(min = 2, message = "Usage Info: Title should contain at least 2 characters.")
    private String title;

    /*
     * Description
     * Should not be null or empty
     * Should contain at least 10 characters
     */
    @NotEmpty
    @Size(min = 10, message = "Usage Info: Description should contain at least 10 characters.")
    private String description;

    /*
     * Content
     * Should not be null or empty     
     */
    @NotEmpty
    private String content;
}
