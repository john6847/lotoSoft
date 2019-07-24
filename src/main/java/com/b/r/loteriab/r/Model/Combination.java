package com.b.r.loteriab.r.Model;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Entity
@Data
@Table(name    = "combination",
        indexes = {@Index(name = "i_combination", columnList = "sequence,combination_type_id")})
public class Combination implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "combination_number_two_digits",
            joinColumns = @JoinColumn(name = "combination_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "number_two_digits_id", referencedColumnName = "id"))
    private List<NumberTwoDigits> numberTwoDigits;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "numberThreeDigits_id")
    private NumberThreeDigits numberThreeDigits;
    private String resultCombination;
    @Nullable
    private double maxPrice;
    private double saleTotal;
    @ManyToOne(optional = false)
    @JoinColumn(name = "combination_type_id")
    private CombinationType combinationType;
    @Min(value = 1)
    private long sequence;
    private boolean enabled;

}
