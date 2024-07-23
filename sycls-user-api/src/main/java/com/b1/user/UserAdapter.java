package com.b1.user;

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
            log.error("유저 이메일을 찾지 못함 | request : {}", email);
            return new IllegalArgumentException(
                    );
        });
    }
}
