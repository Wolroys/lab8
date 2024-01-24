package com.sber.cache;

import com.sber.service.Service;
import com.sber.service.ServiceImpl;
import com.sber.storage.CacheStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CacheProxyTest {
    private CacheProxy cacheProxy;
    private Service service;

    @BeforeEach
    public void setUp() {
        CacheStorage defaultCacheStorage = mock(CacheStorage.class);
        cacheProxy = new CacheProxy("test_root", defaultCacheStorage);
        service = cacheProxy.cache(new ServiceImpl());
    }

    @Test
    public void testCacheProxyCreation() {
        assertNotNull(service);
    }

    @Test
    public void testCachedMethodInvocation() {
        CacheStorage cacheStorage = mock(CacheStorage.class);

        CacheProxy customCacheProxy = new CacheProxy("test_root", cacheStorage);
        Service customService = customCacheProxy.cache(new ServiceImpl());

        List<String> result1 = customService.run("item1", 10.0, new Date());
        List<String> result2 = customService.run("item1", 20.0, new Date());

        assertEquals(result1, result2);

    }

    @Test
    public void testCachedMethodInvocationForWork() {
        CacheStorage cacheStorage = mock(CacheStorage.class);

        CacheProxy customCacheProxy = new CacheProxy("test_root", cacheStorage);
        Service customService = customCacheProxy.cache(new ServiceImpl());

        List<String> result1 = customService.work("item1");
        List<String> result2 = customService.work("item1");

        assertEquals(result1, result2);
    }
}
