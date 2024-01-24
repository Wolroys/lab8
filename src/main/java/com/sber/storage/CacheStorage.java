package com.sber.storage;

public interface CacheStorage {
    void save(String key, Object value);
    Object load(String key);
}
