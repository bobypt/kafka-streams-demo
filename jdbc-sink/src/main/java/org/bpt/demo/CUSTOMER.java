package org.bpt.demo;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
public class CUSTOMER {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;
}
