package com.b.r.loteriab.r.Notification.Model;

import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import lombok.Data;

import java.util.Date;

@Data
public class LastNotification {
    private boolean changed;
    private Date date;
    private Long enterpriseId;
    int type;
    Long idType;
    SampleResponse sampleResponse;
}
