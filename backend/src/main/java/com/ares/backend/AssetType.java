package com.ares.backend;

public enum AssetType {
    BONDS("Bonds", 1),
    RAW_MATERIALS("Raw-Materials", 2),
    REAL_ESTATES("Real-Estates", 3),
    STOCKS("Stocks", 4);

    private final String typeId;
    private final int indexId;

    AssetType(String typeId, int indexId) {
        this.typeId = typeId;
        this.indexId = indexId;
    }

    public String getTypeId() {
        return typeId;
    }

    public int getIndexId() {
        return indexId;
    }

    static public int getMaxTyes() {
        return values().length;
    }

    // Deprecated: Use getTypeId() and getIndexId() instead
    @Deprecated
    public String getId() {
        return "AssetType:" + typeId + "_ResultIndex:" + indexId;
    }
}