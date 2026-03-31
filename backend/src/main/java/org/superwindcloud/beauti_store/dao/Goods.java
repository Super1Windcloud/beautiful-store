package org.superwindcloud.beauti_store.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Goods {

  @jakarta.persistence.Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private Double steamUnitPrice = 0.01;

  private Double neteasecloudPrice = 0.01;

  private Double jbIdePrice = 0.01;

  private Double meituanPrice = 0.01;

  private Double piaoPrice = 0.01;

  private Double girlPrice = 0.01;
}
