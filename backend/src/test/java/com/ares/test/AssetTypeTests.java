package com.ares.test;

import com.ares.backend.AssetType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testAssetType_GetMaxTypes() {
        assertEquals(4, AssetType.getMaxTyes());
    }

    @Test
    @SuppressWarnings("deprecation")
    void testAssetType_GetId_Deprecated() {
        assertEquals("AssetType:Bonds_ResultIndex:0", AssetType.BONDS.getId());
        assertEquals("AssetType:Raw-Materials_ResultIndex:1", AssetType.RAW_MATERIALS.getId());
        assertEquals("AssetType:Real-Estates_ResultIndex:2", AssetType.REAL_ESTATES.getId());
        assertEquals("AssetType:Stocks_ResultIndex:3", AssetType.STOCKS.getId());
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
