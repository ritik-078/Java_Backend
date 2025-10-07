package com.springboot.user_management.service;

import com.springboot.user_management.dto.UserDto;
import com.springboot.user_management.entity.User;

import java.util.List;

public interface UserService  {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long Id);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto userDto);
    void deleteUser(Long Id);
}
