package com.b.r.loteriab.r.Model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Dany on 14/10/2019.
 */
@Entity
@Data
@Table(name = "global_configuration")
public class GlobalConfiguration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean limitCombinationPrice;
    private boolean notifyLimitCombinationPrice;
    private double maxLimitCombinationPrice;
    private boolean transferSaleToAnotherShift;
    private boolean deleteUserTokenAfterAmountOfTime;
    private int userTokenLifeTime;
    private boolean canDeleteTicket;
    private int ticketLifeTime;
    private boolean canReplayTicket;

    private boolean enabled = true;
    @CreatedDate
    private Date CreationDate;
    @LastModifiedDate
    private Date ModificationDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

}
