package com.springboot.blog.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    
    @NotEmpty
    private String email;
    
    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    private String username;
}
