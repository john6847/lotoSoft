package com.b.r.loteriab.r.Model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dany on 04/05/2019.
 */
@Entity
@Data
public class Pos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String serial;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss a")
    private Date creationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss a")
    private Date modificationDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private boolean enabled;
    private boolean deleted = false;
}
