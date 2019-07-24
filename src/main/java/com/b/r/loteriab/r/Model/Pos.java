package com.b.r.loteriab.r.Model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dany on 04/05/2019.
 */
@Entity
@Data
public class Pos implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(unique = true)
    private String macAddress;
    @Column(unique = true)
    private String serial;
    private Date creationDate;
    private Date modificationDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private boolean enabled;
}
