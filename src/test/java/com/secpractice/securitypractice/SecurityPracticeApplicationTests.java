package com.secpractice.securitypractice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityPracticeApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void login_page() throws Exception {
        mockMvc.perform(post("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    void login() throws Exception {
        mockMvc.perform(post("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    void login_logout() throws Exception {
        mockMvc.perform(post("/login"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/logout"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails()
    void login_fail() throws Exception {
        String failMessage = mockMvc.perform(post("/login"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals("\"login fail\"", failMessage);
    }

}
