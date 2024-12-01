package com.artisa.artisa.service;

import com.artisa.artisa.dto.LoginDto;
import com.artisa.artisa.dto.SignUpDtoArtisan;
import com.artisa.artisa.dto.SignUpDtoClient;
import com.artisa.artisa.dto.SignupDtoAdmin;

public interface AuthService {

    String login(LoginDto loginDto);
    String signup(SignUpDtoArtisan signUpDtoArtisan);
    String signup(SignUpDtoClient signUpDtoClient);
    String signup(SignupDtoAdmin signupDtoAdmin);
}
