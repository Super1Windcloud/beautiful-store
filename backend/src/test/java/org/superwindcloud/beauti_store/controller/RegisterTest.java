package org.superwindcloud.beauti_store.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(Register.class)
public class RegisterTest {}

@SpringBootTest
@AutoConfigureMockMvc
class RegisterCotrollerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  public void testSendEmail() throws Exception {
    mockMvc
        .perform(get("/register/email").param("email", "ss1178933440@gmail.com"))
        .andExpect(status().isOk());
  }
}
