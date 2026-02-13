package com.ares.backend;

public enum AssetType {
    BONDS("Bonds", 0),
    RAW_MATERIALS("Raw-Materials", 1),
    REAL_ESTATES("Real-Estates", 2),
    STOCKS("Stocks", 3);

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

    // Deprecated: amountOfTypes in Repository should be used instead
    @Deprecated
    static public int getMaxTyes() {
        return values().length;
    }

    // Deprecated: Use getTypeId() and getIndexId() instead
    @Deprecated
    public String getId() {
        return "AssetType:" + typeId + "_ResultIndex:" + indexId;
    }
}