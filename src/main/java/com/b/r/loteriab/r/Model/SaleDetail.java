package com.b.r.loteriab.r.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Where(clause = "deleted = false")
public class SaleDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 36, nullable = false)
    private double price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "combination_id", nullable=true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Combination combination;
    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "sale_Sale_details",
            joinColumns = {@JoinColumn(name = "saleDetails_id", referencedColumnName = "id", insertable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "sale_id", referencedColumnName = "id", insertable = false, updatable = false)})
    private Sale sale;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private boolean enabled;
    private boolean won;
    private boolean deleted = false;
}
