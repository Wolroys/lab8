package com.sber.cache;

import com.sber.annotation.Cache;
import com.sber.entity.CacheType;
import com.sber.storage.CacheStorage;
import com.sber.storage.FileCacheStorage;
import com.sber.storage.InMemoryCacheStorage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CacheHandler implements InvocationHandler {
    private final Object target;
    private final String rootFolder;
    private final CacheStorage inMemoryCacheStorage;

    public CacheHandler(Object target, String rootFolder) {
        this.target = target;
        this.rootFolder = rootFolder;
        this.inMemoryCacheStorage = new InMemoryCacheStorage();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache cacheAnnotation = method.getAnnotation(Cache.class);

        if (cacheAnnotation != null) {
            String key = generateCacheKey(method.getName(), args, cacheAnnotation.identityBy());
            CacheStorage cacheStorage = getCacheStorage(cacheAnnotation.type());

            Object cachedResult = cacheStorage.load(key);
            if (cachedResult != null) {
                System.out.println("Result loaded from cache for key: " + key);
                return cachedResult;
            }

            Object result = method.invoke(target, args);
            cacheStorage.save(key, result);
            System.out.println("Result saved to cache for key: " + key);

            return result;
        } else {
            return method.invoke(target, args);
        }
    }

    private String generateCacheKey(String methodName, Object[] args, Class<?>[] identityBy) {
        if (identityBy.length == 0) {
            return methodName + Arrays.deepHashCode(args);
        } else {
            StringBuilder keyBuilder = new StringBuilder(methodName);
            for (Class<?> clazz : identityBy) {
                for (Object arg : args) {
                    if (clazz.isInstance(arg)) {
                        keyBuilder.append("_").append(arg);
                        break;
                    }
                }
            }
            return keyBuilder.toString();
        }
    }

    private CacheStorage getCacheStorage(CacheType cacheType) {
        return switch (cacheType) {
            case IN_MEMORY -> inMemoryCacheStorage;
            case FILE -> new FileCacheStorage(rootFolder, cacheType.isZip());
            default -> throw new IllegalArgumentException("Unsupported cache type: " + cacheType);
        };
    }
}
