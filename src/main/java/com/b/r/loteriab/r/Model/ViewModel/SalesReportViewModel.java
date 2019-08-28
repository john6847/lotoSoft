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

    //    https://www.callicoder.com/spring-boot-jpa-hibernate-postgresql-restful-crud-api-example/
//    https://stackoverflow.com/questions/1754411/how-to-select-date-from-datetime-column/30378572#30378572
//    https://blog.mimacom.com/java-8-dates-with-postgresql/
//    spring boot with  postgresql native query example with date
}
