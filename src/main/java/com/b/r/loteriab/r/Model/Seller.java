package com.b.r.loteriab.r.Model;

import com.b.r.loteriab.r.Model.Serializers.CombinationTypeSerializer;
import com.b.r.loteriab.r.Model.Serializers.SellerSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@JsonSerialize(using = SellerSerializer.class)
public class Seller implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int paymentType;
    private Date creationDate;
    private Date modificationDate;
    private Date lastPaymentDate;
    private double percentageCharged;
    @Nullable
    private double amountCharged;
    private double monthlyPercentagePayment;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "pos_id")
    private Pos pos;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;
    @Nullable
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "groups_id")
    private Groups groups;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private boolean enabled;
}
