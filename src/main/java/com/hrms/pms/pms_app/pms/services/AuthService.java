package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.UserDto;

public interface AuthService {
    UserDto registerUser(UserDto userDto);

    //login user
}
