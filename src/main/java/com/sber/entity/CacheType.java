package com.sber.entity;

public enum CacheType {
    IN_MEMORY(false),
    FILE(true);

    private final boolean isZip;

    CacheType(boolean isZip) {
        this.isZip = isZip;
    }

    public boolean isZip(){
        return isZip;
    }
}
