package com.deliorder.api.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password;

    @Column(nullable = false)
    private String name;

    private String nickname;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String imageUrl;

    private Boolean isAlarmEnabled;

    private LocalDateTime deletedAt;

    @Builder
    public User(Long id, String email, String password, String name, String nickname, String phoneNumber,
                UserRole userRole, String imageUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
        this.imageUrl = imageUrl;
    }

    public void updateAlarmSetting(boolean isAlarmEnabled){
        this.isAlarmEnabled = isAlarmEnabled;
    }

    public void deleteUser() {
        this.deletedAt = LocalDateTime.now();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void updatePhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public void updateImageUrl(String newImageUrl) {
        this.imageUrl = newImageUrl;
    }
}
