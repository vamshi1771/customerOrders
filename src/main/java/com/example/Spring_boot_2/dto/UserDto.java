package com.example.Spring_boot_2.dto;

import com.example.Spring_boot_2.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto {
    private Long userId;
    private String userName;
    private String role;
    private String email;
    private Date registeredDate;
    private String region;
    private String gender;
    private String phoneNumber;
    private String address;
    private Boolean isAccountBlocked;
}
