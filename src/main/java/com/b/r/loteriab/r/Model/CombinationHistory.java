package com.b.r.loteriab.r.Model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Entity
@Data
@Table(name = "combination_history")
public class CombinationHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinColumn(name = "shift_id")
    private Shift shift;
    @CreatedDate
    private Date creationDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "combination_id")
    private Combination combination;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private Double saleTotal;
    public boolean enabled = true;
}
