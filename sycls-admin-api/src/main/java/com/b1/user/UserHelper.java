package com.b1.user;

import com.b1.exception.customexception.UserNotFoundException;
import com.b1.exception.errorcode.UserErrorCode;
import com.b1.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "User Helper")
@Component
@RequiredArgsConstructor
public class UserHelper {

    private final UserRepository userRepository;

    public void addUser(final User user) {
        userRepository.save(user);
    }

    public User findByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("유저를 찾지 못함 | request : {}", email);
            return new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        });
    }

    public boolean checkEmailExists(final String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNicknameExists(final String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public List<User> findAllByUsername(final String username) {
        List<User> userList = userRepository.findAllByUsername(username);
        if (userList == null || userList.isEmpty()) {
            log.error("유저를 찾지 못함 : {}", username);
            throw new UserNotFoundException(UserErrorCode.USER_NOT_FOUND);
        }
        return userList;
    }
}
