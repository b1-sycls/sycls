package com.b1.user;

import com.b1.exception.customexception.UserAlreadyDeletedException;
import com.b1.exception.customexception.UserIncorrectPasswordException;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.security.UserDetailsImpl;
import com.b1.user.dto.UserResignRequestDto;
import com.b1.user.dto.UserSignupRequestDto;
import com.b1.user.entity.User;
import com.b1.user.entity.UserStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j(topic = "User Service")
public class UserService {

    private final UserAdapter userAdapter;

    private final PasswordEncoder passwordEncoder;

    public void signup(UserSignupRequestDto requestDto) {

        // 이메일 중복 검사
        userAdapter.userExistsCheckByEmail(requestDto.email());

        // 닉네임 중복 검사
        userAdapter.userExistsCheckByNickname(requestDto.nickname());

        User user = User.addCustomer(
                requestDto.email(),
                requestDto.username(),
                requestDto.nickname(),
                passwordEncoder.encode(requestDto.password()),
                requestDto.phoneNumber()
        );

        userAdapter.addUser(user);
    }

    public void resign(UserResignRequestDto requestDto, UserDetailsImpl user) {

        User getUser = userAdapter.findByEmail(user.getEmail());
        if (getUser.getStatus() == UserStatus.DELETED) {
            log.error("이미 삭제된 유저 | request : {}", getUser.getId());
            throw new UserAlreadyDeletedException(UserErrorCode.USER_ALREADY_DELETED);
        }

        if (!passwordEncoder.matches(requestDto.password(), getUser.getPassword())) {
            log.error("패스워드 불일치");
            throw new UserIncorrectPasswordException(UserErrorCode.USER_PASSWORD_MISMATCH);
        }

        getUser.deleteUser();
    }

}
