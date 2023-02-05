package com.tech.api.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = TablePrefix.PREFIX_TABLE+"cart")
public class Cart extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
