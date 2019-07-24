package com.b.r.loteriab.r.Model.ViewModel;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SampleResponse {
    private String message;
    private List<String> messages;
    private Map body = new HashMap();
}
