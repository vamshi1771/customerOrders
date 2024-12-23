package com.example.Spring_boot_2.entity;


import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "users")
@Check(constraints = "role IN ('ADMIN', 'CUSTOMER')")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "email")
    private String email;

    @Column(name = "registered_date")
    private Date registeredDate;

    @Column(name = "region")
    private String region;

    @Column(name = "gender")
    private String gender;

    @Column (name = "phone_number")
    private String phoneNumber;

    @Column (name = "address")
    private String address;

    @Column(name = "is_account_blocked")
    private Boolean isAccountBlocked = false;
}
