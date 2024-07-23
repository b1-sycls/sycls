package com.b1.security;

import com.b1.user.UserAdapter;
import com.b1.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAdapter userAdapter;

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userAdapter.findByEmail(email);
        return new UserDetailsImpl(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userAdapter.findByEmail(email);
        return new UserDetailsImpl(user);
    }
}
