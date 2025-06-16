package com.example.onlinebookshop.security;

import com.example.onlinebookshop.exceptions.EntityNotFoundException;
import com.example.onlinebookshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new EntityNotFoundException(
                "Can't find user by username " + username));
    }
}
