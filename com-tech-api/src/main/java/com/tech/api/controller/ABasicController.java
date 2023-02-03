package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.intercepter.MyAuthentication;
import com.tech.api.jwt.UserJwt;
import com.tech.api.storage.model.Account;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class ABasicController {

    public long getCurrentUserId(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return authentication.getJwtUser().getAccountId();
    }

    public long getCurrentStoreId(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return authentication.getJwtUser().getPosId();
    }

    public long getCurrentPosId(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return authentication.getJwtUser().getPosId();
    }

    public Account getCurrentAdmin() {
        Account account = new Account();
        account.setId(getCurrentUserId());
        return account;
    }

    public UserJwt getSessionFromToken(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return authentication.getJwtUser();
    }

    public boolean isAdmin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), Constants.USER_KIND_ADMIN);
    }

    public boolean isEmployee(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), Constants.USER_KIND_EMPLOYEE);
    }

    public boolean isCollaborator(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), Constants.USER_KIND_COLLABORATOR);
    }

    public boolean isCustomer(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), Constants.USER_KIND_CUSTOMER);
    }

    public boolean isSuperAdmin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        MyAuthentication authentication = (MyAuthentication)securityContext.getAuthentication();
        return Objects.equals(authentication.getJwtUser().getUserKind(), Constants.USER_KIND_ADMIN) && authentication.getJwtUser().getIsSuperAdmin();
    }
}

