package com.b.r.loteriab.r.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Where(clause = "deleted = false")
public class Ticket implements Serializable {
    public long sequence;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serial;
    private String shortSerial;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "shift_id")
    private Shift shift; // tanda
    private Date emissionDate;
    @JsonIgnore // TODO: Recordar decirle eso a Dany
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private boolean completed;
    private boolean won;
    private boolean enabled;
    private double amountWon;
    private boolean deleted = false;
}
