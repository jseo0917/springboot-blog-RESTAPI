package com.springboot.blog.service;

import com.springboot.blog.Dto.LoginDto;
import com.springboot.blog.Dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
