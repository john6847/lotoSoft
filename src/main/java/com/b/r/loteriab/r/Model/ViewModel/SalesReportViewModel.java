package com.b.r.loteriab.r.Model.ViewModel;

import lombok.Data;

import java.util.Date;

@Data
public class SalesReportViewModel {
    private Date startDate;
    private Date endDate;
    private Long sellerId;
    private Long enterpriseId;
    private Long shiftId;
    private Long groupId;
}
