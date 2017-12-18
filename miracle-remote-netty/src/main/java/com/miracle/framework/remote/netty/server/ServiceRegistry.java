package com.miracle.framework.remote.netty.server;

import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public final class ServiceRegistry {
    
    private static final ServiceRegistry instance = new ServiceRegistry();
    
    private final ConcurrentHashMap<Class, Class> serviceMap = new ConcurrentHashMap<>(512);
    
    public static ServiceRegistry getInstance() {
        return instance;
    }
    
    public <T> void register(final Class<T> serviceInterface, final Class<? extends T> serviceImplementation) {
        serviceMap.putIfAbsent(serviceInterface, serviceImplementation);
    }
    
    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> find(final Class<T> serviceInterface) {
        return serviceMap.get(serviceInterface);
    }
}
