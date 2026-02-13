package com.ares.test;

import com.ares.backend.AssetType;
import com.ares.backend.Repository;
import com.ares.backend.model.Asset;
import com.ares.backend.model.Bonds;
import com.ares.backend.model.RawMaterials;
import com.ares.backend.model.RealEstates;
import com.ares.backend.model.Result;
import com.ares.backend.model.Stocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTests {

    private Repository repository;

    @BeforeEach
    void setUp() {
        repository = new Repository();
    }

    @Test
    void testAddAsset_Bonds() {
        Asset bonds = new Bonds(1000f, 5);
        repository.addAsset(bonds);
        assertEquals(1, repository.getAssets().size());

        Asset retrievedBonds = repository.getAssets().get(0);
        assertEquals(AssetType.BONDS, retrievedBonds.getType());
        assertEquals(1000f, retrievedBonds.getStartcapital());
        assertEquals(5, retrievedBonds.getYears());
        assertEquals(3.0f, retrievedBonds.getInterest());
        assertEquals(5.0f, retrievedBonds.getVolatility());
    }

    @Test
    void testAddAsset_Stocks() {
        Asset stocks = new Stocks(2000f, 10);
        repository.addAsset(stocks);
        assertEquals(1, repository.getAssets().size());

        Asset retrievedStocks = repository.getAssets().get(0);
        assertEquals(AssetType.STOCKS, retrievedStocks.getType());
        assertEquals(2000f, retrievedStocks.getStartcapital());
        assertEquals(10, retrievedStocks.getYears());
        assertEquals(8.0f, retrievedStocks.getInterest());
        assertEquals(15.0f, retrievedStocks.getVolatility());
    }

    @Test
    void testAddAsset_RealEstates() {
        Asset realEstates = new RealEstates(50000f, 20);
        repository.addAsset(realEstates);
        assertEquals(1, repository.getAssets().size());

        Asset retrievedRealEstates = repository.getAssets().get(0);
        assertEquals(AssetType.REAL_ESTATES, retrievedRealEstates.getType());
        assertEquals(50000f, retrievedRealEstates.getStartcapital());
        assertEquals(20, retrievedRealEstates.getYears());
        assertEquals(5.0f, retrievedRealEstates.getInterest());
        assertEquals(7.0f, retrievedRealEstates.getVolatility());
    }

    @Test
    void testAddAsset_RawMaterials() {
        Asset rawMaterials = new RawMaterials(3000f, 15);
        repository.addAsset(rawMaterials);
        assertEquals(1, repository.getAssets().size());

        Asset retrievedRawMaterials = repository.getAssets().get(0);
        assertEquals(AssetType.RAW_MATERIALS, retrievedRawMaterials.getType());
        assertEquals(3000f, retrievedRawMaterials.getStartcapital());
        assertEquals(15, retrievedRawMaterials.getYears());
        assertEquals(6.0f, retrievedRawMaterials.getInterest());
        assertEquals(10.0f, retrievedRawMaterials.getVolatility());
    }

    @Test
    void testAddAsset_MultipleAssets() {
        repository.addAsset(new Bonds(1000f, 5));
        repository.addAsset(new Stocks(2000f, 10));
        repository.addAsset(new RealEstates(50000f, 20));

        assertEquals(3, repository.getAssets().size());
        assertEquals(AssetType.BONDS, repository.getAssets().get(0).getType());
        assertEquals(AssetType.STOCKS, repository.getAssets().get(1).getType());
        assertEquals(AssetType.REAL_ESTATES, repository.getAssets().get(2).getType());
    }


    @Test
    void testAddAsset() {
        Asset bonds = new Bonds(1000f, 5);
        repository.addAsset(bonds);

        assertEquals(1, repository.getAssets().size());
        assertEquals(bonds, repository.getAssets().get(0));
    }



    @Test
    void testGetAssets_EmptyList() {
        List<Asset> assets = repository.getAssets();
        assertNotNull(assets);
        assertTrue(assets.isEmpty());
    }

    @Test
    void testGetResults_EmptyListForYearAndType() {
        // Test new nested map API
        assertFalse(repository.hasResults());
        assertEquals(0, repository.getYearCount());
        assertEquals(0, repository.getTotalResultCount());
        assertTrue(repository.getAvailableYears().isEmpty());
        assertTrue(repository.getAllResultsAsList().isEmpty());
    }

    @Test
    void testGetResultsForYearForYearAndType() {
        repository.addAsset(new Asset(AssetType.BONDS, 100f, 1, 5, 3.0f) {});
        repository.addAsset(new Asset(AssetType.STOCKS, 100f, 1, 5, 3.0f) {});

        repository.storeResult(new Result(AssetType.BONDS, 1, 1030f, 3.0f));
        repository.storeResult(new Result(AssetType.STOCKS, 1, 2160f, 8.0f));
        repository.storeResult(new Result(AssetType.BONDS, 2, 1061f, 3.0f));

        Map<AssetType, Result> year1 = repository.getResultsForYear(1);
        assertEquals(2, year1.size());
        assertTrue(year1.containsKey(AssetType.BONDS));
        assertTrue(year1.containsKey(AssetType.STOCKS));

        Map<AssetType, Result> year2 = repository.getResultsForYear(2);
        assertEquals(1, year2.size());
        assertTrue(year2.containsKey(AssetType.BONDS));
    }

    @Test
    void testGetResultsForAssetTypeForYearAndTypeAsList() {
        repository.addAsset(new Asset(AssetType.BONDS, 100f, 1, 5, 3.0f) {});

        repository.storeResult(new Result(AssetType.BONDS, 0, 1000f, 0f));
        repository.storeResult(new Result(AssetType.BONDS, 1, 1030f, 3.0f));
        repository.storeResult(new Result(AssetType.BONDS, 2, 1061f, 3.0f));

        List<Result> bondsResults = repository.getResultsForAssetTypeAsList(AssetType.BONDS);
        assertEquals(3, bondsResults.size());
        assertEquals(0, bondsResults.get(0).getYear());
        assertEquals(1, bondsResults.get(1).getYear());
        assertEquals(2, bondsResults.get(2).getYear());
    }

    @Test
    void testHasResult() {
        repository.addAsset(new Asset(AssetType.BONDS, 100f, 1, 5, 3.0f) {});
        repository.storeResult(new Result(AssetType.BONDS, 1, 1030f, 3.0f));

        assertTrue(repository.hasResult(1, AssetType.BONDS));
        assertFalse(repository.hasResult(2, AssetType.BONDS));
        assertFalse(repository.hasResult(1, AssetType.STOCKS));
    }

    @Test
    void testGetAvailableYears() {
        repository.addAsset(new Asset(AssetType.BONDS, 100f, 1, 5, 3.0f) {});

        repository.storeResult(new Result(AssetType.BONDS, 0, 1000f, 0f));
        repository.storeResult(new Result(AssetType.BONDS, 2, 1061f, 3.0f));
        repository.storeResult(new Result(AssetType.BONDS, 5, 1159f, 3.0f));

        List<Integer> years = repository.getAvailableYears();
        assertEquals(3, years.size());
        assertTrue(years.contains(0));
        assertTrue(years.contains(2));
        assertTrue(years.contains(5));
    }

    @Test
    void testGetTotalCapitalForYear() {
        repository.addAsset(new Asset(AssetType.BONDS, 100f, 1, 5, 3.0f) {});
        repository.addAsset(new Asset(AssetType.STOCKS, 100f, 1, 5, 3.0f) {});

        repository.storeResult(new Result(AssetType.BONDS, 1, 1030f, 3.0f));
        repository.storeResult(new Result(AssetType.STOCKS, 1, 2160f, 8.0f));

        float total = repository.getTotalCapitalForYear(1);
        assertEquals(3190f, total, 0.01f);
    }

    @Test
    void testFindMaxCapital() {
        repository.addAsset(new Asset(AssetType.BONDS, 100f, 1, 5, 3.0f) {});
        repository.addAsset(new Asset(AssetType.STOCKS, 100f, 1, 5, 3.0f) {});

        repository.storeResult(new Result(AssetType.BONDS, 1, 1030f, 3.0f));
        repository.storeResult(new Result(AssetType.STOCKS, 1, 2160f, 8.0f));
        repository.storeResult(new Result(AssetType.BONDS, 2, 1061f, 3.0f));
        repository.storeResult(new Result(AssetType.STOCKS, 2, 2333f, 8.0f));

        var maxResult = repository.findMaxCapital();
        assertTrue(maxResult.isPresent());
        assertEquals(2333f, maxResult.get().getCapital(), 0.01f);
        assertEquals(AssetType.STOCKS, maxResult.get().getType());
    }

    @Test
    void testGetResultsForYearRangeForYearAndType() {
        repository.addAsset(new Asset(AssetType.BONDS, 100f, 1, 5, 3.0f) {});

        for (int year = 0; year <= 5; year++) {
            repository.storeResult(new Result(AssetType.BONDS, year, 1000f + year * 30, 3.0f));
        }

        var range = repository.getResultsForYearRange(2, 4);
        assertEquals(3, range.size());
        assertTrue(range.containsKey(2));
        assertTrue(range.containsKey(3));
        assertTrue(range.containsKey(4));
        assertFalse(range.containsKey(1));
        assertFalse(range.containsKey(5));
    }

    @Test
    void testClearResults() {
        repository.addAsset(new Asset(AssetType.BONDS, 100f, 1, 5, 3.0f) {});
        repository.storeResult(new Result(AssetType.BONDS, 1, 1030f, 3.0f));

        assertTrue(repository.hasResults());
        assertEquals(1, repository.getTotalResultCount());

        repository.clearResults();

        assertFalse(repository.hasResults());
        assertEquals(0, repository.getTotalResultCount());
    }
}

