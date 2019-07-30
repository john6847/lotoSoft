package com.b.r.loteriab.r.Model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dany on 22/04/2019.
 */
@Entity
@Data
public class Enterprise implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int numberOfEmployee;
    private String phone;
    private String rnc;
    private String logoUrl;
    private String subDomain;
    private String identifier;
    private int sequence;
    private Date creationDate;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    private Date ModificationDate;
    private boolean enabled;
}