package com.tech.api.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
public class ResponseListObj<T> {
    private List<T> data;
    private Integer page;
    private Integer totalPage;
    private Long totalElements;

    public ResponseListObj() {

    }
    public ResponseListObj(List<T> data) {
        this.data = data;
    }
    public ResponseListObj(List<T> data, Page page) {
        this.data = data;
        this.page = page.getNumber();
        this.totalPage = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
