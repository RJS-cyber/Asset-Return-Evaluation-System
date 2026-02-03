package com.ares.backend;

public enum AssetType {
    BONDS(1),
    RAW_MATERIALS(2),
    REAL_ESTATES(3),
    STOCKS(4);

    private final int id;

    AssetType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}