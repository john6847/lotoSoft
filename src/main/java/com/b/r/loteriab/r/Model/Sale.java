package com.b.r.loteriab.r.Model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Sale implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinColumn(name = "shift_id")
    private Shift shift; // tanda
    private Date date;
    private int saleStatus;
    private double totalAmount;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,  mappedBy = "sale")
    private List<SaleDetail> saleDetails;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "pos_id")
    private Pos pos;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private Seller seller;
    private boolean enabled;
}
