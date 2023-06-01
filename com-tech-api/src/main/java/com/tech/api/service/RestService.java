package com.tech.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.api.constant.Constants;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ResponseListObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class RestService {
    private String baseUrl = "https://dev-online-gateway.ghn.vn";
    private String recommendUrl = "http://127.0.0.1:5000/";

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    public <T> ApiMessageDto<ResponseListObj<T>> LIST(Boolean isRecommendation,String path, String authorization, final Class<T> clazz) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            if (authorization != null) {
                headers.add("Authorization", authorization);
            }
            HttpEntity entity = new HttpEntity(headers);
            ResolvableType resolvableType = ResolvableType.forClassWithGenerics(ResponseListObj.class,clazz);
            ResponseEntity<ApiMessageDto<ResponseListObj<T>>> response = restTemplate.exchange((isRecommendation ? recommendUrl : baseUrl) + path, HttpMethod.GET, entity, ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(ApiMessageDto.class, resolvableType).getType()));
            return response.getBody();
        } catch (Exception ex) {
            log.error("GET>>error: " + ex.getMessage(), ex);
            return null;
        }
    }



    public <T> ApiMessageDto<T> GET( String path, String authorization, final Class<T> clazz){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            if(authorization!=null){
                headers.add("Authorization", authorization);
            }
            HttpEntity entity = new HttpEntity(headers);
            ParameterizedTypeReference type = new ParameterizedTypeReference<ApiMessageDto<T>>() {
                public Type getType() {
                    return new MyParameterizedTypeImpl((ParameterizedType) super.getType(), new Type[] {clazz});
                }};
            ResponseEntity<ApiMessageDto<T>> response = restTemplate.exchange(baseUrl + path, HttpMethod.GET, entity, type);
            return response.getBody();
        } catch (Exception ex) {
            log.error("GET>>error: " + ex.getMessage(), ex);
            return null;
        }
    }

    public <T, A> ApiMessageDto<T> POST(Long shopId,A input, String path, String authorization, final Class<T> clazz){
        try {
            HttpHeaders headers = new HttpHeaders();
            //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("token", Constants.token);
            if(shopId != null) headers.add("shopId",shopId.toString());
            HttpEntity<A> entity = new HttpEntity<>(input, headers);
            ParameterizedTypeReference type = new ParameterizedTypeReference<ApiMessageDto<T>>() {
                public Type getType() {
                    return new MyParameterizedTypeImpl((ParameterizedType) super.getType(), new Type[] {clazz});
                }};
            ResponseEntity<ApiMessageDto<T>> response = restTemplate.exchange(baseUrl+path, HttpMethod.POST, entity,type);
            return response.getBody();
        } catch (Exception ex) {
            log.error("POST>>error: " + ex.getMessage(), ex);
            return null;
        }
    }

    public <T, A> ApiMessageDto<List<T>> POST_FOR_LIST(Long shopId, A input, String path, String authorization, final Class<T> clazz){
        try {
            HttpHeaders headers = new HttpHeaders();
            //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("token", Constants.token);
            if(shopId != null) headers.add("shopId",shopId.toString());
            HttpEntity<A> entity = new HttpEntity<>(input, headers);
            ResolvableType resolvableType = ResolvableType.forClassWithGenerics(Collection.class,clazz);
            ResponseEntity<ApiMessageDto<List<T>>> response = restTemplate.exchange(baseUrl+path, HttpMethod.POST, entity,ParameterizedTypeReference.forType(ResolvableType.forClassWithGenerics(ApiMessageDto.class, resolvableType).getType()));
            return response.getBody();
        } catch (Exception ex) {
            log.error("POST>>error: " + ex.getMessage(), ex);
            return null;
        }
    }
}

