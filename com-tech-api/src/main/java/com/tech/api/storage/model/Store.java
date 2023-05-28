package com.tech.api.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = TablePrefix.PREFIX_TABLE + "store")
public class Store extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address_details")
    private String addressDetails;

    @Column(name = "is_accept_order")
    private Boolean isAcceptOrder;

    private String phone;

    private String province;
    private Long shopId;

    private Long provinceCode;
    private Long districtCode;
    private String wardCode;
}
