package com.b.r.loteriab.r.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app.websocket")
public class WebSocketProperties {
    /**
     * Prefix used for WebSocket destination mappings
     */
    private String applicationPrefix = "/topic";
    /**
     * Prefix used by topics
     */
    private String topicPrefix = "/topic";
    /**
     * Endpoint that can be used to connect to
     */
    private String endpoint = "/live";
    /**
     * Allowed origins
     */
//    private String[] allowedOrigins = {
//        "http://localhost:3200",
//        "http://lotosof.com:3200",
//        "http://www.lotosof.com:3200/",
//        "https://lotosof.com",
//        "https://lotosof.com/",
//        "https://www.lotosof.com",
//        "https://www.lotosof.com/",
//    };
}
