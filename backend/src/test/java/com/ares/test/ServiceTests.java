package com.ares.test;

import com.ares.backend.AssetType;
import com.ares.backend.Service;
import com.ares.backend.model.Result;
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
    void testSimulation_WithNoAssets() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.simulation();
        });
        assertTrue(exception.getMessage().contains("No assets found in the repository"));
    }

    @Test
    void testGetResults_ReturnsCorrect2DArrayStructure() {
        List<AssetType> assetTypes = Arrays.asList(
                AssetType.BONDS,
                AssetType.STOCKS,
                AssetType.REAL_ESTATES,
                AssetType.RAW_MATERIALS
        );
        int years = 5;
        float startCapital = 10000f;
        service.storeData(years, startCapital, assetTypes);

        service.simulation();
        Result[][] results = service.getResults();

        assertNotNull(results);
        assertEquals(years + 1, results.length, "Should have results for years 0 through " + years);

        for (int yearIndex = 0; yearIndex <= years; yearIndex++) {
            assertNotNull(results[yearIndex]);
            assertTrue(results[yearIndex].length >= 4);
        }

        for (AssetType assetType : assetTypes) {
            Result result = results[0][assetType.getIndexId()];
            assertNotNull(result);
            assertEquals(0, result.year());
            assertEquals(startCapital, result.capital());
            assertEquals(0f, result.development());
            assertEquals(assetType, result.type());
        }

        for (int yearIndex = 1; yearIndex <= years; yearIndex++) {
            for (AssetType assetType : assetTypes) {
                Result currentResult = results[yearIndex][assetType.getIndexId()];
                Result previousResult = results[yearIndex - 1][assetType.getIndexId()];

                assertNotNull(currentResult);
                assertEquals(yearIndex, currentResult.year());
                assertEquals(assetType, currentResult.type());

                double expectedDevelopment = currentResult.capital() - previousResult.capital();
                assertEquals(expectedDevelopment, currentResult.development());

                assertTrue(currentResult.capital() > 0);
            }
        }

        assertEquals(AssetType.BONDS, results[0][AssetType.BONDS.getIndexId()].type());
        assertEquals(AssetType.STOCKS, results[0][AssetType.STOCKS.getIndexId()].type());
        assertEquals(AssetType.REAL_ESTATES, results[0][AssetType.REAL_ESTATES.getIndexId()].type());
        assertEquals(AssetType.RAW_MATERIALS, results[0][AssetType.RAW_MATERIALS.getIndexId()].type());
    }
}
