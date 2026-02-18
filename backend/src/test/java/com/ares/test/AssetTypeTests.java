package com.ares.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ares.backend.util.AssetType;
import org.junit.jupiter.api.Test;

class AssetTypeTests {

    @Test
    void testAssetType_GetTypeId() {
        assertEquals("Bonds", AssetType.BONDS.getTypeId());
        assertEquals("Raw-Materials", AssetType.RAW_MATERIALS.getTypeId());
        assertEquals("Real-Estates", AssetType.REAL_ESTATES.getTypeId());
        assertEquals("Stocks", AssetType.STOCKS.getTypeId());
    }

    @Test
    void testAssetType_GetIndexId() {
        assertEquals(0, AssetType.BONDS.getIndexId());
        assertEquals(1, AssetType.RAW_MATERIALS.getIndexId());
        assertEquals(2, AssetType.REAL_ESTATES.getIndexId());
        assertEquals(3, AssetType.STOCKS.getIndexId());
    }

    @Test
    void testAssetType_Values() {
        AssetType[] values = AssetType.values();
        assertEquals(4, values.length);
        assertEquals(AssetType.BONDS, values[0]);
        assertEquals(AssetType.RAW_MATERIALS, values[1]);
        assertEquals(AssetType.REAL_ESTATES, values[2]);
        assertEquals(AssetType.STOCKS, values[3]);
    }
}
