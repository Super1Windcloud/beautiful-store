package org.superwindcloud.beauti_store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class ApiResponse<T> {
  private int code;
  private String message;
  private T data;
}
