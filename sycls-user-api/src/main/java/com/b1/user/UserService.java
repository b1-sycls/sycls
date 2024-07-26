package com.b1.user;

import com.b1.auth.AuthService;
import com.b1.exception.customexception.TokenException;
import com.b1.exception.customexception.UserAlreadyDeletedException;
import com.b1.exception.customexception.UserEmailDuplicatedException;
import com.b1.exception.customexception.UserIncorrectPasswordException;
import com.b1.exception.customexception.UserNicknameDuplicatedException;
import com.b1.exception.errorcode.TokenErrorCode;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.security.UserDetailsImpl;
import com.b1.user.dto.UserProfileResponseDto;
import com.b1.user.dto.UserResignRequestDto;
import com.b1.user.dto.UserSignupRequestDto;
import com.b1.user.entity.User;
import com.b1.user.entity.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "User Service")
public class UserService {

    private final UserHelper userHelper;
    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    public void signup(UserSignupRequestDto requestDto) {
        // 이메일 중복 검사
        if (userHelper.checkEmailExists(requestDto.email())) {
            log.error("이메일 중복 | email : {}", requestDto.email());
            throw new UserEmailDuplicatedException(UserErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }

        // 닉네임 중복 검사
        if (userHelper.checkNicknameExists(requestDto.nickname())) {
            log.error("닉네임 중복 | nickname : {}", requestDto.nickname());
            throw new UserNicknameDuplicatedException(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS);
        }

        if (!authService.verifyCode(requestDto.email(), requestDto.code())) {
            throw new TokenException(TokenErrorCode.CODE_MISMATCH);
        }

        User user = User.addCustomer(
                requestDto.email(),
                requestDto.username(),
                requestDto.nickname(),
                passwordEncoder.encode(requestDto.password()),
                requestDto.phoneNumber()
        );

        userHelper.addUser(user);
    }

    public void resign(UserResignRequestDto requestDto, UserDetailsImpl user) {
        User getUser = userHelper.findByEmail(user.getEmail());
        if (UserStatus.isDeleted(getUser.getStatus())) {
            log.error("이미 삭제된 유저 | request : {}", getUser.getId());
            throw new UserAlreadyDeletedException(UserErrorCode.USER_ALREADY_DELETED);
        }

        if (!passwordEncoder.matches(requestDto.password(), getUser.getPassword())) {
            log.error("패스워드 불일치");
            throw new UserIncorrectPasswordException(UserErrorCode.USER_PASSWORD_MISMATCH);
        }

        getUser.deleteUser();
    }

    public UserProfileResponseDto getProfile(User user) {
        return UserProfileResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
