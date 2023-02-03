package com.tech.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiMessageDto<T> {
    private Boolean result = true;
    private String code = null;
    private T data = null;
    private String message = null;

    public ApiMessageDto(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ApiMessageDto(String message) {
        this.message = message;
    }
}
