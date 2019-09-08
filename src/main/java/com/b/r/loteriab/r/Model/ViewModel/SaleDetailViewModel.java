package com.b.r.loteriab.r.Model.ViewModel;

import lombok.Data;

@Data
public class SaleDetailViewModel {
    private double price;
    private String combination;
    private String product;
    private long combinationTypeId;
    private long enterpriseId;
}
