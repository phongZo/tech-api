package com.tech.api.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "customer_address")
public class CustomerAddress extends Auditable<String>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "address_details")
    private String addressDetails;

    @Column(name = "receiver_full_name")
    private String receiverFullName;

    @Column(name = "phone", columnDefinition = "varchar(10)")
    private String phone;

    @Column(name = "is_default")
    private Boolean isDefault;

    private Integer typeAddress;

    @Column(name = "note")
    private String note;
}
