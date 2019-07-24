package com.b.r.loteriab.r.Model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Dany on 04/05/2019.
 */
@Entity
@Data
public class NumberTwoDigits implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numberInStringFormat;
    private int numberInIntegerFormat;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private boolean enabled = true;
}
