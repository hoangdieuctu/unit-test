package com.hoangdieuctu.boot.sample.service;

import com.hoangdieuctu.boot.sample.model.User;
import com.hoangdieuctu.boot.sample.model.dto.UserDto;
import com.hoangdieuctu.boot.sample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto getUser(String name) {
        User user = userRepository.findOne(name);
        return convert(user);
    }

    private UserDto convert(User user) {
        return new UserDto(user.getId(), user.getName());
    }
}
