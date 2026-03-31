package org.superwindcloud.beauti_store.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@RequiredArgsConstructor
public class Account {

  @jakarta.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String password;
  private String username;
  private String avatarUrl;

  private String balance = "0.00";

  @CreationTimestamp
  @Column(name = "created_date", updatable = false)
  private LocalDateTime createdDate;

  private String loginType = "email";
}
