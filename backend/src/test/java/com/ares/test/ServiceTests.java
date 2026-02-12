package com.ares.test;

import com.ares.backend.AssetType;
import com.ares.backend.Service;
import com.ares.backend.model.Asset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceTests {
    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();
    }

    @Test
    void testStoreData_SingleAssetType() {
        List<AssetType> assetTypes = Collections.singletonList(AssetType.BONDS);
        service.storeData(5, 1000f, assetTypes);

        List<Asset> assets = service.getAssets();
        assertEquals(1, assets.size());
        assertEquals(AssetType.BONDS, assets.get(0).getType());
        assertEquals(1000f, assets.get(0).getStartcapital());
        assertEquals(5, assets.get(0).getYears());
    }

    @Test
    void testStoreData_MultipleAssetTypes() {
        List<AssetType> assetTypes = Arrays.asList(
                AssetType.BONDS,
                AssetType.STOCKS,
                AssetType.REAL_ESTATES,
                AssetType.RAW_MATERIALS
        );
        service.storeData(10, 5000f, assetTypes);

        List<Asset> assets = service.getAssets();
        assertEquals(4, assets.size());
        assertEquals(AssetType.BONDS, assets.get(0).getType());
        assertEquals(AssetType.STOCKS, assets.get(1).getType());
        assertEquals(AssetType.REAL_ESTATES, assets.get(2).getType());
        assertEquals(AssetType.RAW_MATERIALS, assets.get(3).getType());

        // Verify all have same start capital and years
        for (Asset asset : assets) {
            assertEquals(5000f, asset.getStartcapital());
            assertEquals(10, asset.getYears());
        }
    }

    @Test
    void testStoreData_NullAssetTypes() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.storeData(5, 1000f, null);
        });
        assertTrue(exception.getMessage().contains("Asset types cannot be null or empty"));
    }

    @Test
    void testStoreData_EmptyAssetTypes() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.storeData(5, 1000f, Collections.emptyList());
        });
        assertTrue(exception.getMessage().contains("Asset types cannot be null or empty"));
    }

    @Test
    void testStoreData_ZeroAmount() {
        List<AssetType> assetTypes = Collections.singletonList(AssetType.BONDS);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.storeData(5, 0f, assetTypes);
        });
        assertTrue(exception.getMessage().contains("Amount must be greater than zero"));
    }

    @Test
    void testStoreData_NegativeAmount() {
        List<AssetType> assetTypes = Collections.singletonList(AssetType.BONDS);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.storeData(5, -1000f, assetTypes);
        });
        assertTrue(exception.getMessage().contains("Amount must be greater than zero"));
    }

    @Test
    void testStoreData_ZeroYears() {
        List<AssetType> assetTypes = Collections.singletonList(AssetType.BONDS);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.storeData(0, 1000f, assetTypes);
        });
        assertTrue(exception.getMessage().contains("Years must be greater than zero"));
    }

    @Test
    void testStoreData_NegativeYears() {
        List<AssetType> assetTypes = Collections.singletonList(AssetType.BONDS);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.storeData(-5, 1000f, assetTypes);
        });
        assertTrue(exception.getMessage().contains("Years must be greater than zero"));
    }

    @Test
    void testGetAssets_FromService() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.getAssets();
        });
        assertTrue(exception.getMessage().contains("No assets found in the repository"));
    }
}
