package com.tech.api.dto.account;

import lombok.Data;

import java.util.Date;

@Data
public class LoginDto {
    private String username;
    private String token;
    private String fullName;
    private long id;
    private Date expired;
    private Integer kind;
    private Long customerId;
    private Long storeId;
    private String storeName;
    private Boolean isSuperAdmin = false;
}
