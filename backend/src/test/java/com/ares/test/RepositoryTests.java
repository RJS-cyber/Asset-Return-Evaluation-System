package com.ares.test;

import com.ares.backend.AssetType;
import com.ares.backend.Repository;
import com.ares.backend.model.Asset;
import com.ares.backend.model.Bonds;
import com.ares.backend.model.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositoryTests {

    private Repository repository;

    @BeforeEach
    void setUp() {
        repository = new Repository();
    }

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
        repository.addAsset(new Asset(100f, 1, 5, 3.0f, AssetType.BONDS) {});
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
    void testStoreResult_SingleResult() {
        repository.addAsset(new Asset(100f, 1, 5, 3.0f, AssetType.BONDS) {});
        Result result = new Result(AssetType.BONDS, 1, 1050f, 3.0f);
        repository.storeResult(result);

        assertEquals(1, repository.getResults().size());
        Result[] firstYearResults = repository.getResults().get(0);
        assertEquals(result, firstYearResults[AssetType.BONDS.getIndexId()]);
    }

    @Test
    void testStoreResult_MultipleResultsSameYear() {
        repository.addAsset(new Asset(100f, 1, 5, 3.0f, AssetType.BONDS) {});
        repository.addAsset(new Asset(100f, 1, 5, 3.0f, AssetType.STOCKS) {});
        Result bondsResult = new Result(AssetType.BONDS, 1, 1050f, 3.0f);
        Result stocksResult = new Result(AssetType.STOCKS, 1, 2100f, 8.0f);

        repository.storeResult(bondsResult);
        repository.storeResult(stocksResult);

        assertEquals(1, repository.getResults().size());
        Result[] firstYearResults = repository.getResults().get(0);
        assertEquals(bondsResult, firstYearResults[AssetType.BONDS.getIndexId()]);
        assertEquals(stocksResult, firstYearResults[AssetType.STOCKS.getIndexId()]);
    }

    @Test
    void testStoreResult_MultipleDifferentYears() {
        repository.addAsset(new Asset(100f, 1, 5, 3.0f, AssetType.BONDS) {});
        Result year1Result = new Result(AssetType.BONDS, 1, 1050f, 3.0f);
        Result year2Result = new Result(AssetType.BONDS, 2, 1100f, 3.0f);

        repository.storeResult(year1Result);
        repository.storeResult(year2Result);

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
}
