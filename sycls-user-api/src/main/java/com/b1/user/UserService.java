package com.b1.user;

import com.b1.SyclsUserApiApplication;
import com.b1.user.dto.UserSignupRequestDto;
import com.b1.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserAdapter userAdapter;

    private final PasswordEncoder passwordEncoder;

    public void signup(UserSignupRequestDto requestDto) {

        User user = User.addCustomer(
                requestDto.email(),
                requestDto.username(),
                requestDto.nickname(),
                passwordEncoder.encode(requestDto.password()),
                requestDto.phoneNumber()
        );

        userAdapter.addUser(user);
    }
}
