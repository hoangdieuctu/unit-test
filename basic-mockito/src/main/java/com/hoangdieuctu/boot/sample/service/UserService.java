package com.hoangdieuctu.boot.sample.service;

import com.hoangdieuctu.boot.sample.model.User;
import com.hoangdieuctu.boot.sample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String name) {
        return userRepository.findOne(name);
    }

}
