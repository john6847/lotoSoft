package com.b.r.loteriab.r.Model.ViewModel;

import lombok.Data;

@Data
public class CombinationVm {
    private Long id;
    private double maxPrice;
    private Long combinationTypeId;
    private boolean enabled;
    private boolean changeState;
    private boolean changeMaxPrice;
}
