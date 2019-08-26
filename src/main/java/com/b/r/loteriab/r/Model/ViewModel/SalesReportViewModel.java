package com.b.r.loteriab.r.Model.ViewModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SalesReportViewModel {
    private String startDate;
    private String endDate;
    private Long sellerId;
    private Long enterpriseId;
    private Long shiftId;
    private Long groupId;
    private String seller_name;
    private double sale_result;
    private double sale_total;
    private double amount_won;
    private double net_sale;
    private double salary;
}
