package com.tech.api.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "customer")
public class Customer extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "wallet_money")
    private Double walletMoney = 0d;

    private Integer gender;
    
    private LocalDate birthday;

    @Column(name = "note", columnDefinition = "varchar(1000)")
    private String note;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CustomerAddress> customerAddresses;

    private Integer loyaltyLevel = 0;   //0: bronze, 1: silver, 2: gold, 3: platinum, 4: diamond, 5: black diamond
    private Double loyaltyPoint = 0d;
    private Double point = 0d;
}
