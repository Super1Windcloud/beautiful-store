package org.superwindcloud.beauti_store.dao;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "girl_photo")
@Data
@NoArgsConstructor
public class GirlPhoto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Lob
  @Column(columnDefinition = "BYTEA")
  private byte[] data;

  private String contentType;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date creationDate;
}
