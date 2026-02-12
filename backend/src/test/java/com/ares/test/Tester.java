package com.ares.test;

import com.ares.backend.AssetType;
import com.ares.backend.Repository;
import com.ares.backend.Service;
import com.ares.backend.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Tester {

    private Repository repository;
    private Service service;

    @BeforeEach
    void setUp() {
        repository = new Repository();
        service = new Service();
    }

    // ============= Repository Tests =============

    @Test
    void testCreateAssetByType_Bonds() {
        repository.createAssetByType(AssetType.BONDS, 1000f, 5);
        assertEquals(1, repository.getAssets().size());

        Asset bonds = repository.getAssets().get(0);
        assertEquals(AssetType.BONDS, bonds.getType());
        assertEquals(1000f, bonds.getStartcapital());
        assertEquals(5, bonds.getYears());
        assertEquals(3.0f, bonds.getInterest());
        assertEquals(5.0f, bonds.getVolatility());
    }

    @Test
    void testCreateAssetByType_Stocks() {
        repository.createAssetByType(AssetType.STOCKS, 2000f, 10);
        assertEquals(1, repository.getAssets().size());

        Asset stocks = repository.getAssets().get(0);
        assertEquals(AssetType.STOCKS, stocks.getType());
        assertEquals(2000f, stocks.getStartcapital());
        assertEquals(10, stocks.getYears());
        assertEquals(8.0f, stocks.getInterest());
        assertEquals(15.0f, stocks.getVolatility());
    }

    @Test
    void testCreateAssetByType_RealEstates() {
        repository.createAssetByType(AssetType.REAL_ESTATES, 50000f, 20);
        assertEquals(1, repository.getAssets().size());

        Asset realEstates = repository.getAssets().get(0);
        assertEquals(AssetType.REAL_ESTATES, realEstates.getType());
        assertEquals(50000f, realEstates.getStartcapital());
        assertEquals(20, realEstates.getYears());
        assertEquals(5.0f, realEstates.getInterest());
        assertEquals(7.0f, realEstates.getVolatility());
    }

    @Test
    void testCreateAssetByType_RawMaterials() {
        repository.createAssetByType(AssetType.RAW_MATERIALS, 3000f, 15);
        assertEquals(1, repository.getAssets().size());

        Asset rawMaterials = repository.getAssets().get(0);
        assertEquals(AssetType.RAW_MATERIALS, rawMaterials.getType());
        assertEquals(3000f, rawMaterials.getStartcapital());
        assertEquals(15, rawMaterials.getYears());
        assertEquals(6.0f, rawMaterials.getInterest());
        assertEquals(10.0f, rawMaterials.getVolatility());
    }

    @Test
    void testCreateAssetByType_MultipleAssets() {
        repository.createAssetByType(AssetType.BONDS, 1000f, 5);
        repository.createAssetByType(AssetType.STOCKS, 2000f, 10);
        repository.createAssetByType(AssetType.REAL_ESTATES, 50000f, 20);

        assertEquals(3, repository.getAssets().size());
        assertEquals(AssetType.BONDS, repository.getAssets().get(0).getType());
        assertEquals(AssetType.STOCKS, repository.getAssets().get(1).getType());
        assertEquals(AssetType.REAL_ESTATES, repository.getAssets().get(2).getType());
    }

    @Test
    void testCreateAssetByType_NullType() {
        assertThrows(NullPointerException.class, () -> {
            repository.createAssetByType(null, 1000f, 5);
        });
    }

    @Test
    void testAddAsset() {
        Asset bonds = new Bonds(1000f, 5);
        repository.addAsset(bonds);

        assertEquals(1, repository.getAssets().size());
        assertEquals(bonds, repository.getAssets().get(0));
    }

    @Test
    void testCreateResult() {
        repository.createResult(AssetType.BONDS, 1, 1050f, 3.0f);

        assertEquals(1, repository.getResults().size());
        Result[] firstYearResults = repository.getResults().get(0);

        assertNotNull(firstYearResults[AssetType.BONDS.getIndexId()]);
        Result result = firstYearResults[AssetType.BONDS.getIndexId()];
        assertEquals(AssetType.BONDS, result.getType());
        assertEquals(1, result.getYear());
        assertEquals(1050f, result.getCapital());
        assertEquals(3.0f, result.getInterest());
    }

    @Test
    void testAddResult_SingleResult() {
        Result result = new Result(AssetType.BONDS, 1, 1050f, 3.0f);
        repository.addResult(result);

        assertEquals(1, repository.getResults().size());
        Result[] firstYearResults = repository.getResults().get(0);
        assertEquals(result, firstYearResults[AssetType.BONDS.getIndexId()]);
    }

    @Test
    void testAddResult_MultipleResultsSameYear() {
        Result bondsResult = new Result(AssetType.BONDS, 1, 1050f, 3.0f);
        Result stocksResult = new Result(AssetType.STOCKS, 1, 2100f, 8.0f);

        repository.addResult(bondsResult);
        repository.addResult(stocksResult);

        assertEquals(1, repository.getResults().size());
        Result[] firstYearResults = repository.getResults().get(0);
        assertEquals(bondsResult, firstYearResults[AssetType.BONDS.getIndexId()]);
        assertEquals(stocksResult, firstYearResults[AssetType.STOCKS.getIndexId()]);
    }

    @Test
    void testAddResult_MultipleDifferentYears() {
        Result year1Result = new Result(AssetType.BONDS, 1, 1050f, 3.0f);
        Result year2Result = new Result(AssetType.BONDS, 2, 1100f, 3.0f);

        repository.addResult(year1Result);
        repository.addResult(year2Result);

        assertEquals(2, repository.getResults().size());
        assertEquals(year1Result, repository.getResults().get(0)[AssetType.BONDS.getIndexId()]);
        assertEquals(year2Result, repository.getResults().get(1)[AssetType.BONDS.getIndexId()]);
    }

    @Test
    void testGetAssets_EmptyList() {
        List<Asset> assets = repository.getAssets();
        assertNotNull(assets);
        assertTrue(assets.isEmpty());
    }

    @Test
    void testGetResults_EmptyList() {
        List<Result[]> results = repository.getResults();
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    // ============= Service Tests =============

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
        List<Asset> assets = service.getAssets();
        assertNotNull(assets);
        assertTrue(assets.isEmpty());
    }

    // ============= AssetType Tests =============

    @Test
    void testAssetType_GetTypeId() {
        assertEquals("Bonds", AssetType.BONDS.getTypeId());
        assertEquals("Raw-Materials", AssetType.RAW_MATERIALS.getTypeId());
        assertEquals("Real-Estates", AssetType.REAL_ESTATES.getTypeId());
        assertEquals("Stocks", AssetType.STOCKS.getTypeId());
    }

    @Test
    void testAssetType_GetIndexId() {
        assertEquals(1, AssetType.BONDS.getIndexId());
        assertEquals(2, AssetType.RAW_MATERIALS.getIndexId());
        assertEquals(3, AssetType.REAL_ESTATES.getIndexId());
        assertEquals(4, AssetType.STOCKS.getIndexId());
    }

    @Test
    void testAssetType_GetMaxTypes() {
        assertEquals(4, AssetType.getMaxTyes());
    }

    @Test
    @SuppressWarnings("deprecation")
    void testAssetType_GetId_Deprecated() {
        assertEquals("AssetType:Bonds_ResultIndex:1", AssetType.BONDS.getId());
        assertEquals("AssetType:Raw-Materials_ResultIndex:2", AssetType.RAW_MATERIALS.getId());
        assertEquals("AssetType:Real-Estates_ResultIndex:3", AssetType.REAL_ESTATES.getId());
        assertEquals("AssetType:Stocks_ResultIndex:4", AssetType.STOCKS.getId());
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

    // ============= Model Tests =============

    @Test
    void testBonds_Creation() {
        Bonds bonds = new Bonds(1000f, 5);
        assertEquals(1000f, bonds.getStartcapital());
        assertEquals(5, bonds.getYears());
        assertEquals(3.0f, bonds.getInterest());
        assertEquals(5.0f, bonds.getVolatility());
        assertEquals(AssetType.BONDS, bonds.getType());
    }

    @Test
    void testStocks_Creation() {
        Stocks stocks = new Stocks(2000f, 10);
        assertEquals(2000f, stocks.getStartcapital());
        assertEquals(10, stocks.getYears());
        assertEquals(8.0f, stocks.getInterest());
        assertEquals(15.0f, stocks.getVolatility());
        assertEquals(AssetType.STOCKS, stocks.getType());
    }

    @Test
    void testRealEstates_Creation() {
        RealEstates realEstates = new RealEstates(50000f, 20);
        assertEquals(50000f, realEstates.getStartcapital());
        assertEquals(20, realEstates.getYears());
        assertEquals(5.0f, realEstates.getInterest());
        assertEquals(7.0f, realEstates.getVolatility());
        assertEquals(AssetType.REAL_ESTATES, realEstates.getType());
    }

    @Test
    void testRawMaterials_Creation() {
        RawMaterials rawMaterials = new RawMaterials(3000f, 15);
        assertEquals(3000f, rawMaterials.getStartcapital());
        assertEquals(15, rawMaterials.getYears());
        assertEquals(6.0f, rawMaterials.getInterest());
        assertEquals(10.0f, rawMaterials.getVolatility());
        assertEquals(AssetType.RAW_MATERIALS, rawMaterials.getType());
    }

    @Test
    void testResult_Creation() {
        Result result = new Result(AssetType.BONDS, 5, 1500f, 3.5f);
        assertEquals(AssetType.BONDS, result.getType());
        assertEquals(5, result.getYear());
        assertEquals(1500f, result.getCapital());
        assertEquals(3.5f, result.getInterest());
    }

    @Test
    void testResult_AllGetters() {
        Result result = new Result(AssetType.STOCKS, 10, 5000f, 8.2f);
        assertNotNull(result.getType());
        assertTrue(result.getYear() > 0);
        assertTrue(result.getCapital() > 0);
        assertTrue(result.getInterest() > 0);
    }
}
