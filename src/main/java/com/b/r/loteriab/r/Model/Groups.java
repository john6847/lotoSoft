package com.b.r.loteriab.r.Model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dany on 22/04/2019.
 */
@Entity
@Data
public class Groups implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Address address;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_seller_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Seller parentSeller;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Enterprise enterprise;
    private Date creationDate;
    private Date ModificationDate;
    private boolean enabled;
}