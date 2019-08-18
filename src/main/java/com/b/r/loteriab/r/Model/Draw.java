package com.b.r.loteriab.r.Model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 04/05/2019.
 */
@Entity
@Data
public class Draw implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinColumn(name = "shift_id")
    private Shift shift; // tanda
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name="draw_numberTwoDigits", joinColumns=@JoinColumn(name="draw_id"), inverseJoinColumns=@JoinColumn(name="numberTwoDigits_id"))
//    private List<NumberTwoDigits> numberTwoDigits;
    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "numberThreeDigits_id")
    private NumberThreeDigits numberThreeDigits;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "firstDraw_id")
    private NumberTwoDigits firstDraw;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "secondDraw_id")
    private NumberTwoDigits secondDraw;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "thirdDraw_id")
    private NumberTwoDigits thirdDraw;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date drawDate;
    private Date creationDate;
    private Date modificationDate;
    private boolean enabled;
    private Double amountSold;
    private Double amountWon;
    private Double amountLost;


}
