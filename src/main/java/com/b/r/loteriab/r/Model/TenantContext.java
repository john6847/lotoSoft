package com.b.r.loteriab.r.Model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TenantContext implements Runnable {
    final static Map<String, Long> userContextPerUserId = new ConcurrentHashMap<>();
    private String username;
    private Long enterprise;

    public TenantContext(String username, Long enterprise) {
        this.username = username;
        this.enterprise = enterprise;
    }

    public static Map<String, Long> getUserContextPerUserId() {
        return userContextPerUserId;
    }

    @Override
    public void run() {
        userContextPerUserId.put(username, enterprise);
    }

    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

}
