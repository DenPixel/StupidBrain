package com.pixel.stupidbrain.service;

import com.pixel.stupidbrain.entity.User;
import com.pixel.stupidbrain.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class JPAUserDetailsService implements UserDetailsService {
    private static final String ROLE_PREFIX = "ROLE_";

    private final UserRepository userRepository;

    public JPAUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = userRepository
                .findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("User " + nickname + " not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()))
                        .collect(Collectors.toList())
        );
    }

}
