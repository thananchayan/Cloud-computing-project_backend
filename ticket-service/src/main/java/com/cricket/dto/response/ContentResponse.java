package com.cricket.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentResponse<T> {
  private String status;
  private String statusCode;
  private String message;
  private String type;
  private T data;

  public ContentResponse(String type, T data, String status, String statusCode, String message) {
    this.type = type;
    this.data = data;
    this.status = status;
    this.statusCode = statusCode;
    this.message = message;
  }
}
