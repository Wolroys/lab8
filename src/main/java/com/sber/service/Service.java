package com.sber.service;

import com.sber.annotation.Cache;
import com.sber.entity.CacheType;

import java.util.Date;
import java.util.List;

public interface Service {

    @Cache(type = CacheType.FILE, fileNamePrefix = "data", isZip = true, identityBy = {String.class, double.class})
    List<String> run(String item, double value, Date date);

    @Cache(type = CacheType.IN_MEMORY, limit = 100_000L)
    List<String> work(String item);
}
