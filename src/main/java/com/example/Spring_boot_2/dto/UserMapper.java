package com.example.Spring_boot_2.dto;

import com.example.Spring_boot_2.entity.User;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .userId(user.getUser_id())
                .userName(user.getUserName())
                .role(user.getRole())
                .email(user.getEmail())
                .registeredDate(user.getRegisteredDate())
                .region(user.getRegion())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .isAccountBlocked(user.getIsAccountBlocked())
                .build();
    }

    public static User toUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUser_id(userDto.getUserId());
        user.setUserName(userDto.getUserName());
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        user.setRegisteredDate(userDto.getRegisteredDate());
        user.setRegion(userDto.getRegion());
        user.setGender(userDto.getGender());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());
        user.setIsAccountBlocked(userDto.getIsAccountBlocked());
        // Password is intentionally excluded
        return user;
    }
}