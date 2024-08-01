package com.b1.user;

import com.b1.auth.AuthService;
import com.b1.exception.customexception.EmailCodeException;
import com.b1.exception.customexception.UserAlreadyDeletedException;
import com.b1.exception.customexception.UserEmailDuplicatedException;
import com.b1.exception.customexception.UserIncorrectPasswordException;
import com.b1.exception.customexception.UserNicknameDuplicatedException;
import com.b1.exception.errorcode.EmailAuthErrorCode;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.security.UserDetailsImpl;
import com.b1.user.dto.UserProfileResponseDto;
import com.b1.user.dto.UserResignRequestDto;
import com.b1.user.dto.UserSignupRequestDto;
import com.b1.user.entity.User;
import com.b1.user.entity.UserRole;
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

    private final String adminKey = "7JeQ67iM66as7Yuw7LyT7Ja065Oc66+87YKk";

    public void signup(final UserSignupRequestDto requestDto) {
        //관리자 키 인증
        if (!this.adminKey.equals(requestDto.adminKey())) {
            log.error("관리자 키 불일치");
            throw new IllegalArgumentException("관리자 키가 일치하지 않습니다.");
        }

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
            log.error("Email 에 Code 가 일치하지 않습니다. : {} , {}", requestDto.email(), requestDto.code());
            throw new EmailCodeException(EmailAuthErrorCode.CODE_MISMATCH);
        }

        User user = User.addCustomer(
                requestDto.email(),
                requestDto.username(),
                requestDto.nickname(),
                passwordEncoder.encode(requestDto.password()),
                requestDto.phoneNumber(),
                UserRole.ADMIN
        );

        userHelper.addUser(user);
    }

    public void resign(final UserResignRequestDto requestDto, final UserDetailsImpl user) {
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

    public UserProfileResponseDto getProfile(final User user) {
        return UserProfileResponseDto.of(
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
