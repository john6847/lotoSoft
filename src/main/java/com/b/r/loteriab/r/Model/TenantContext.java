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
//    private static ThreadLocal<String> userContext
//            = new ThreadLocal<>();
//    private String enterprise;
//
//    public static void setCurrentTenant(String tenant) {
//        userContext.set(tenant);
//    }
//
//    public static void clear() {
//        currentTenant.set(null);
//    }
//
//    public static String getCurrentTenant(){
//        return currentTenant.get();
//    }
//
//
//    public TenantContext(String enterprise) {
//        this.enterprise = enterprise;
//    }
//
//    @Override
//    public void run() {
//        userContext.set(enterprise);
//        System.out.println("thread context for given userId: "
//                + enterprise + " is: " + userContext.get());
//    }


//    /** Scope identifier for tenant scope: "tenant". */
//    public static final String SCOPE_TENANT = "tenant";
//
//    /** Id of default tenant */
//    public static final String DEFAULT_TENANT = "default";
//
//    /** Thread bound tenant context holder */
//    private static ThreadLocal<Object> currentTenant = ThreadLocal.withInitial(() -> DEFAULT_TENANT);
//
//    /**
//     * Set tenant context with tenant id.
//     *
//     * @param tenant Tenant id
//     */
//    public static void setCurrentTenant(Object tenant) {
//        Objects.requireNonNull(tenant, "Tenant id can not be null");
//        currentTenant.set(tenant);
//    }
//
//    /**
//     * Returns current tenant context.
//     *
//     * @return Tenant context
//     */
//    public static Object getCurrentTenant() {
//        return currentTenant.get();
//    }
//
//    public static String getCurrentTenantId() {
//        return getCurrentTenant().toString();
//    }
//
//    /**
//     * Returns current tenant identifier.
//     *
//     * @return Tenant id
//     */
//    public String getTenant() {
//        return currentTenant.get().toString();
//    }
//
//    /**
//     * Returns current tenant context.
//     *
//     * @return Tenant context
//     */
//    public Object getScopeContext() {
//        return currentTenant.get();
//    }
//
//    /** Removes the ThredLocaMap from the current tenant (ThreadLocal) */
//    public static void clearCurrentTenant() {
//        currentTenant.remove();
//    }


    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

//    public static String getCurrentTenant(){
//        return currentTenant.get();
//    }
//
//    public static void setCurrentTenant(String tenant){
//        currentTenant.set(tenant);
//    }
//
//    public static void clear(){
//        currentTenant.set(null);
//    }
//
//    @Override
//    public void run() {
//        System.out.println("Context "+currentTenant.get());
//    }
}
