package com.b1.user;

import com.b1.exception.customexception.UserEmailDuplicatedException;
import com.b1.exception.customexception.UserNicknameDuplicatedException;
import com.b1.exception.customexception.UserNotFoundException;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "User Adapter")
@Component
@RequiredArgsConstructor
public class UserAdapter {

    private final UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("유저를 찾지 못함 | request : {}", email);
            return new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        });
    }

    public void userExistsCheckByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            log.error("이메일 중복 | email : {}", email);
            throw new UserEmailDuplicatedException(UserErrorCode.USER_EMAIL_ALREADY_EXISTS);
        }
    }

    public void userExistsCheckByNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            log.error("닉네임 중복 | nickname : {}", nickname);
            throw new UserNicknameDuplicatedException(UserErrorCode.USER_NICKNAME_ALREADY_EXISTS);
        }
    }

}
