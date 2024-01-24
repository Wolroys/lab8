package com.sber.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service{
    @Override
    public List<String> run(String item, double value, Date date) {
        List<String> result = new ArrayList<>();
        result.add(item + "_" + value + "_" + date.toString());
        return result;
    }

    @Override
    public List<String> work(String item) {
        List<String> result = new ArrayList<>();
        result.add(item);
        return result;
    }
}
