package com.b.r.loteriab.r.Model;

import com.b.r.loteriab.r.Model.Serializers.CombinationTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dany on 04/05/2019.
 */
@Entity
@Data
@JsonSerialize(using = CombinationTypeSerializer.class)
public class CombinationType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @OneToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "products_id")
    private Products products;
    @Nullable
    private double payedPrice;
    @Nullable
    private double payedPriceFirstDraw;
    @Nullable
    private double payedPriceSecondDraw;
    @Nullable
    private double payedPriceThirdDraw;
    @Column(nullable = false)
    private Date creationDate;
    @Column(nullable = false)
    private Date modificationDate;
    private String note;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private boolean enabled;
}
