package com.tech.api.storage.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "employee")
public class Employee {

    @Id
    @Column(name = "account_id")
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @MapsId
    private Account account;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Category department;

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Category job;

    private String note;
}
