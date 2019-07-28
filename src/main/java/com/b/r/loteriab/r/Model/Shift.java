package com.b.r.loteriab.r.Model;

import com.b.r.loteriab.r.Model.Serializers.CombinationTypeSerializer;
import com.b.r.loteriab.r.Model.Serializers.ShiftSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Dany on 22/04/2019.
 */
@Entity
@Data
@JsonSerialize(using = ShiftSerializer.class)
public class Shift implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String openTime;
    private String closeTime;
    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;
    private boolean enabled;
}