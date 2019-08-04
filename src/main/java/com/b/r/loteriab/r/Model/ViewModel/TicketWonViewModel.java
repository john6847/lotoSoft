package com.b.r.loteriab.r.Model.ViewModel;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.Shift;
import lombok.Data;

import java.util.Date;

@Data
public class TicketWonViewModel {
    private Enterprise enterprise;
    private Date emissionDate;
    private Shift shift;
    private Seller seller;
}
