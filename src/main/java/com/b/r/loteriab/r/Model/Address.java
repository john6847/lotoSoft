package com.b.r.loteriab.r.Model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Address implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String sector;
    private String city;
    private String region;
    private String zipCode;
    private String street;
    private int number;
    private String phone;
    private String email;
    private Date creationDate;
    private Date modificationDate;
}
