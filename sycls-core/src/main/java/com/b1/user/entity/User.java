package com.b1.user.entity;

import com.b1.common.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserLoginType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    //    @Column(nullable = true, unique = true)
    private Long kakaoId;

    @Builder(access = AccessLevel.PRIVATE)
    private User(final String email, final String username, final String nickname,
            final String password,
            final String phoneNumber, final UserStatus status, final UserLoginType type,
            final UserRole role,
            final Long kakaoId) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.type = type;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    public static User addCustomer(final String email, final String username, final String nickname,
            final String password,
            final String phoneNumber, final UserLoginType type, final UserRole role) {
        return User.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .password(password)
                .phoneNumber(phoneNumber)
                .status(UserStatus.ACTIVE)
                .type(type)
                .role(role)
                .build();
    }

    public void changePassword(final String password) {
        this.password = password;
    }

    public void deleteUser() {
        this.status = UserStatus.DELETED;
    }

    public User kakaoIdUpdate(final Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void updateProfile(final String nickname, final String phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
}
