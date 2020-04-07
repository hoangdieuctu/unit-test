package com.hoangdieuctu.boot.sample.repository;

import com.hoangdieuctu.boot.sample.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public User findOne(String name) {
        User user = new User();
        user.setId("1");
        user.setName(name);

        return user;
    }

}
