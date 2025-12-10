package com.deliorder.api.service;

import com.deliorder.api.common.exception.ErrorCode;
import com.deliorder.api.common.exception.HandledException;
import com.deliorder.api.entity.User;
import com.deliorder.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new HandledException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.updatePassword(encodedPassword);

        return userRepository.save(newUser);
    }
}
