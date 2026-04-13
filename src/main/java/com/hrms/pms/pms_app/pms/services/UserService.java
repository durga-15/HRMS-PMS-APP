package com.hrms.pms.pms_app.pms.services;

import com.hrms.pms.pms_app.pms.dtos.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto updateUser(UserDto userDto, String userId);

    UserDto getUserById(String userId);

    void deleteUser(String userId);

    Iterable<UserDto>  getAllUsers();
}
