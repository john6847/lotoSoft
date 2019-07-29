package com.b.r.loteriab.r.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Date ModificationDate;
    private boolean enabled;
}