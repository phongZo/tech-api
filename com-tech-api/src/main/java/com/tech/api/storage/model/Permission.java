package com.tech.api.storage.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE+"permission")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", unique =  true)
    private String name;
    @Column(name = "action")
    private String action;
    @Column(name = "show_menu")
    private Boolean showMenu;

    private String description;
    @Column(name = "name_group")
    private String nameGroup;
}
