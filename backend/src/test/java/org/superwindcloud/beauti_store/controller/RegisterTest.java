package org.superwindcloud.beauti_store.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.superwindcloud.beauti_store.repository.AccountRepo;
import org.superwindcloud.beauti_store.services.CustomOAuth2UserService;
import org.superwindcloud.beauti_store.services.Email;

@WebMvcTest(Register.class)
@AutoConfigureMockMvc(addFilters = false)
class RegisterTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private AccountRepo accountRepo;

  @MockitoBean private RedisTemplate<String, String> redisTemplate;

  @MockitoBean private Email emailService;

  @MockitoBean private CustomOAuth2UserService customOAuth2UserService;

  @BeforeEach
  @SuppressWarnings("unchecked")
  void setUp() {
    ValueOperations<String, String> valueOperations = Mockito.mock(ValueOperations.class);
    when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    when(valueOperations.get(anyString())).thenReturn(null);
  }

  @Test
  void testSendEmail() throws Exception {
    mockMvc
        .perform(get("/api/register/email").param("email", "ss1178933440@gmail.com"))
        .andExpect(status().isOk())
        .andExpect(content().string("send email success"));
  }
}
