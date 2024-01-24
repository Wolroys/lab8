package com.sber.cache;

import com.sber.storage.CacheStorage;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Proxy;

@RequiredArgsConstructor
public class CacheProxy {

    private final String dir;
    private final CacheStorage cacheStorage;

    @SuppressWarnings("unchecked")
    public <T> T cache(T service){
        return (T) Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new CacheHandler(service, dir)
        );
    }
}
