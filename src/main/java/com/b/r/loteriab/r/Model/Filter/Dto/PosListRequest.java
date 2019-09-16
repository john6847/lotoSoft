package com.b.r.loteriab.r.Model.Filter.Dto;

import lombok.Data;

@Data
public class PosListRequest {
    String id;
    String description;
    String serial;
    Boolean enabled;
    String creationDate;
    String enterpriseId;
    String sortId;
    String sortDescription;
    String sortCreationDate;
    String sortSerial;
    String sortState;
}
