package com.hoangdieuctu.boot.sample.service;

import com.hoangdieuctu.boot.sample.model.User;
import com.hoangdieuctu.boot.sample.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Before
    public void setup() {
        when(user.getId()).thenReturn("10");
        when(user.getName()).thenReturn("hoangdieuctu");

        when(userRepository.findOne("hoangdieuctu")).thenReturn(user);
    }

    @Test
    public void testGetUser() {
        User found = userService.getUser("hoangdieuctu");
        Assert.assertEquals("hoangdieuctu", found.getName());
        Assert.assertEquals("10", found.getId());
    }
}
