package com.b.r.loteriab.r.Model.ViewModel;

import lombok.Data;

@Data
public class SalesReportViewModel {
    private String startDate;
    private String endDate;
    private Long sellerId;
    private Long enterpriseId;
    private Long shiftId;
    private Long groupId;


    //    https://www.callicoder.com/spring-boot-jpa-hibernate-postgresql-restful-crud-api-example/
//    https://stackoverflow.com/questions/1754411/how-to-select-date-from-datetime-column/30378572#30378572
//    https://blog.mimacom.com/java-8-dates-with-postgresql/
//    spring boot with  postgresql native query example with date
}
