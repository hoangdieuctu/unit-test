package com.hoangdieuctu.boot.sample.controller;

import com.hoangdieuctu.boot.sample.model.dto.UserDto;
import com.hoangdieuctu.boot.sample.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private UserDto userDto;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDto = new UserDto("10", "hoangdieuctu");
        when(userService.getUser("hoangdieuctu")).thenReturn(userDto);
    }

    @Test
    public void testGetUser() throws Exception {
        this.mockMvc.perform(get("/user?name=hoangdieuctu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("10")))
                .andExpect(jsonPath("$.name", is("hoangdieuctu")))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())));
    }
}
