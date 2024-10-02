package com.alakh.service;

import com.alakh.config.JwtGenerator;
import com.alakh.modal.User;
import com.alakh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public User getUserProfile(String jwt) {
        String email = JwtGenerator.getEmailFromJwtToken(jwt);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
