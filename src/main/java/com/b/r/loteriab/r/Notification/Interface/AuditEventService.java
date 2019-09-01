package com.b.r.loteriab.r.Notification.Interface;
import com.b.r.loteriab.r.Model.ViewModel.SampleResponse;
import com.b.r.loteriab.r.Notification.Model.LastNotification;
import org.apache.logging.log4j.message.SimpleMessage;

import java.util.Date;

/**
 * Created by John on 18/8/2019.
 */
public interface AuditEventService {
   void sendMessage(SampleResponse sampleResponse, Long enterpriseId, LastNotification last);
}
