package com.b.r.loteriab.r.Model.ViewModel;

import com.b.r.loteriab.r.Model.Enterprise;
import com.b.r.loteriab.r.Model.Pos;
import com.b.r.loteriab.r.Model.Seller;
import com.b.r.loteriab.r.Model.Shift;
import lombok.Data;

import java.util.List;

@Data
public class SaleViewModel {
    private Shift shift;
    private double totalAmount;
    private List<SaleDetailViewModel> saleDetails;
    private Pos pos;
    private Enterprise enterprise;
    private Seller seller;
    private boolean enabled;
}
