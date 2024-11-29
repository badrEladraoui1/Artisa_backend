package com.artisa.artisa.service;

import com.artisa.artisa.dto.LoginDto;
import com.artisa.artisa.dto.SignUpDto;

public interface AuthService {

    String login(LoginDto loginDto);
    String signup(SignUpDto signUpDto, String role);
}
