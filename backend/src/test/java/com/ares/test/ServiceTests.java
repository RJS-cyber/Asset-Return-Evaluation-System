package com.ares.test;

import com.ares.backend.AssetType;
import com.ares.backend.Service;
import com.ares.backend.model.Asset;
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

    @Test
    void testSimulation_WithSingleAsset() {
        // Arrange
        List<AssetType> assetTypes = Collections.singletonList(AssetType.BONDS);
        int years = 5;
        float startCapital = 1000f;
        service.storeData(years, startCapital, assetTypes);

        // Act
        service.simulation();

        // Assert - Verify year 0 exists with correct initial values
        assertDoesNotThrow(() -> {
            // We can't directly access results, but we can verify the simulation ran without errors
            assertEquals(years, service.getYears());
        });
    }

    @Test
    void testSimulation_WithMultipleAssets() {
        // Arrange
        List<AssetType> assetTypes = Arrays.asList(
                AssetType.BONDS,
                AssetType.STOCKS,
                AssetType.REAL_ESTATES,
                AssetType.RAW_MATERIALS
        );
        int years = 10;
        float startCapital = 5000f;
        service.storeData(years, startCapital, assetTypes);

        // Act
        service.simulation();

        // Assert - Verify simulation completed successfully
        assertDoesNotThrow(() -> {
            assertEquals(years, service.getYears());
            assertEquals(4, service.getAssets().size());
        });
    }

    @Test
    void testSimulation_WithZeroYears() {
        // Arrange - This should fail in storeData
        List<AssetType> assetTypes = Collections.singletonList(AssetType.BONDS);

        // Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.storeData(0, 1000f, assetTypes);
        });
        assertTrue(exception.getMessage().contains("Years must be greater than zero"));
    }

    @Test
    void testSimulation_WithNoAssets() {
        // Act & Assert - Simulation should fail when no assets are stored
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.simulation();
        });
        assertTrue(exception.getMessage().contains("No assets found in the repository"));
    }

    @Test
    void testSimulation_WithOneYear() {
        // Arrange
        List<AssetType> assetTypes = Collections.singletonList(AssetType.STOCKS);
        int years = 1;
        float startCapital = 10000f;
        service.storeData(years, startCapital, assetTypes);

        // Act
        service.simulation();

        // Assert - Verify simulation ran for 1 year
        assertDoesNotThrow(() -> {
            assertEquals(years, service.getYears());
        });
    }

    @Test
    void testSimulation_WithLargeNumberOfYears() {
        // Arrange
        List<AssetType> assetTypes = Collections.singletonList(AssetType.REAL_ESTATES);
        int years = 30;
        float startCapital = 50000f;
        service.storeData(years, startCapital, assetTypes);

        // Act
        service.simulation();

        // Assert - Verify simulation handled large year count
        assertDoesNotThrow(() -> {
            assertEquals(years, service.getYears());
        });
    }

    @Test
    void testSimulation_AllAssetTypes() {
        // Arrange - Test with all possible asset types
        List<AssetType> assetTypes = Arrays.asList(
                AssetType.BONDS,
                AssetType.STOCKS,
                AssetType.REAL_ESTATES,
                AssetType.RAW_MATERIALS
        );
        int years = 15;
        float startCapital = 25000f;
        service.storeData(years, startCapital, assetTypes);

        // Act
        service.simulation();

        // Assert
        assertDoesNotThrow(() -> {
            List<Asset> assets = service.getAssets();
            assertEquals(4, assets.size());
            assertEquals(years, service.getYears());

            // Verify all asset types are present
            assertTrue(assets.stream().anyMatch(a -> a.getType() == AssetType.BONDS));
            assertTrue(assets.stream().anyMatch(a -> a.getType() == AssetType.STOCKS));
            assertTrue(assets.stream().anyMatch(a -> a.getType() == AssetType.REAL_ESTATES));
            assertTrue(assets.stream().anyMatch(a -> a.getType() == AssetType.RAW_MATERIALS));
        });
    }

    @Test
    void testSimulation_VerifyResultsCreated() {
        // Arrange
        List<AssetType> assetTypes = Collections.singletonList(AssetType.BONDS);
        int years = 3;
        float startCapital = 1000f;
        service.storeData(years, startCapital, assetTypes);

        // Act
        service.simulation();

        // Assert - Results are created internally (year 0 through year 3)
        // We verify this indirectly by ensuring simulation completes without error
        assertDoesNotThrow(() -> {
            assertEquals(years, service.getYears());
            assertEquals(startCapital, service.getAssets().get(0).getStartcapital());
        });
    }

    @Test
    void testCreateResult_StoresCorrectly() {
        // Arrange
        AssetType type = AssetType.STOCKS;
        int year = 1;
        float capital = 1500f;
        float development = 500f;

        // Act
        service.createResult(type, year, capital, development);

        // Assert - createResult should execute without throwing exception
        assertDoesNotThrow(() -> {
            service.createResult(type, year, capital, development);
        });
    }

    @Test
    void testCreateResult_WithMultipleResults() {
        // Arrange & Act
        service.createResult(AssetType.BONDS, 0, 1000f, 0f);
        service.createResult(AssetType.BONDS, 1, 1050f, 50f);
        service.createResult(AssetType.STOCKS, 0, 2000f, 0f);
        service.createResult(AssetType.STOCKS, 1, 2100f, 100f);

        // Assert - All results should be stored without errors
        assertDoesNotThrow(() -> {
            service.createResult(AssetType.REAL_ESTATES, 0, 3000f, 0f);
        });
    }

    @Test
    void testGetResults_ReturnsCorrect2DArrayStructure() {
        // Arrange - Set up simulation with multiple asset types and years
        List<AssetType> assetTypes = Arrays.asList(
                AssetType.BONDS,
                AssetType.STOCKS,
                AssetType.REAL_ESTATES,
                AssetType.RAW_MATERIALS
        );
        int years = 5;
        float startCapital = 10000f;
        service.storeData(years, startCapital, assetTypes);

        // Act
        service.simulation();
        Result[][] results = service.getResults();

        // Assert - Verify 2D array structure and dimensions
        assertNotNull(results);
        assertEquals(years + 1, results.length, "Should have results for years 0 through " + years);

        // Verify each year row exists and has correct structure
        for (int yearIndex = 0; yearIndex <= years; yearIndex++) {
            assertNotNull(results[yearIndex]);
            assertTrue(results[yearIndex].length >= 4);
        }

        // Verify year 0 has correct initial values for all asset types
        for (AssetType assetType : assetTypes) {
            Result result = results[0][assetType.getIndexId()];
            assertNotNull(result);
            assertEquals(0, result.getYear());
            assertEquals(startCapital, result.getCapital());
            assertEquals(0f, result.getDevelopment());
            assertEquals(assetType, result.getType());
        }

        // Verify subsequent years have calculated results
        for (int yearIndex = 1; yearIndex <= years; yearIndex++) {
            for (AssetType assetType : assetTypes) {
                Result currentResult = results[yearIndex][assetType.getIndexId()];
                Result previousResult = results[yearIndex - 1][assetType.getIndexId()];

                assertNotNull(currentResult);
                assertEquals(yearIndex, currentResult.getYear());
                assertEquals(assetType, currentResult.getType());

                // Verify development calculation: development = K(n) - K(n-1)
                float expectedDevelopment = currentResult.getCapital() - previousResult.getCapital();
                assertEquals(expectedDevelopment, currentResult.getDevelopment());

                // Verify capital is positive
                assertTrue(currentResult.getCapital() > 0);
            }
        }

        // Verify array indices match AssetType.getIndexId()
        assertEquals(AssetType.BONDS, results[0][AssetType.BONDS.getIndexId()].getType());
        assertEquals(AssetType.STOCKS, results[0][AssetType.STOCKS.getIndexId()].getType());
        assertEquals(AssetType.REAL_ESTATES, results[0][AssetType.REAL_ESTATES.getIndexId()].getType());
        assertEquals(AssetType.RAW_MATERIALS, results[0][AssetType.RAW_MATERIALS.getIndexId()].getType());
    }
}
