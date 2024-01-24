package com.sber.storage;

import java.util.HashMap;

public class InMemoryCacheStorage implements CacheStorage{
    private final HashMap<String, Object> cacheMap = new HashMap<>();

    @Override
    public void save(String key, Object value) {
        cacheMap.put(key, value);
    }

    @Override
    public Object load(String key) {
        return cacheMap.get(key);
    }
}
