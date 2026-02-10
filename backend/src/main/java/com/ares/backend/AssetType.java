package com.ares.backend;

public enum AssetType {
    BONDS("AssetType_Bonds"),
    RAW_MATERIALS("AssetType_Raw_Materials"),
    REAL_ESTATES("AssetType_Real_Estates"),
    STOCKS("AssetType_Stocks");

    private final String id;

    AssetType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}