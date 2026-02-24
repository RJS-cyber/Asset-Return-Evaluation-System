package com.ares.test;

import static org.junit.jupiter.api.Assertions.*;

import com.ares.backend.AssetType;
import com.ares.backend.Repository;
import com.ares.backend.model.Asset;
import com.ares.backend.model.Bonds;
import com.ares.backend.model.RawMaterials;
import com.ares.backend.model.RealEstates;
import com.ares.backend.model.Result;
import com.ares.backend.model.Stocks;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals(0.03f, retrievedBonds.getInterest());
        assertEquals(0.02f, retrievedBonds.getVolatility());
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
        assertEquals(0.08f, retrievedStocks.getInterest());
        assertEquals(0.12f, retrievedStocks.getVolatility());
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
        assertEquals(0.06f, retrievedRealEstates.getInterest());
        assertEquals(0.04f, retrievedRealEstates.getVolatility());
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
        assertEquals(0.05f, retrievedRawMaterials.getInterest());
        assertEquals(0.09f, retrievedRawMaterials.getVolatility());
    }

    @Test
    void testAddAsset_MultipleAssets() {
        repository.addAsset(new Bonds(1000f, 5));
        repository.addAsset(new Stocks(2000f, 10));
        repository.addAsset(new RealEstates(50000f, 20));

        assertEquals(3, repository.getAssets().size());
        assertEquals(AssetType.BONDS, repository.getAssets().get(0).getType());
        assertEquals(AssetType.STOCKS, repository.getAssets().get(1).getType());
        assertEquals(
            AssetType.REAL_ESTATES,
            repository.getAssets().get(2).getType()
        );
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
    void testClearRepo() {
        repository.addAsset(new Asset(AssetType.BONDS, 100f, 1, 5, 3.0f) {});
        repository.storeResult(new Result(AssetType.BONDS, 1, 1030f, 3.0f));

        assertTrue(hasResults(repository.getAllResultsRaw()));
        assertEquals(1, getTotalResultCount(repository.getAllResultsRaw()));

        repository.clearRepo();

        assertFalse(hasResults(repository.getAllResultsRaw()));
        assertEquals(0, getTotalResultCount(repository.getAllResultsRaw()));
    }

    private boolean hasResults(
        Map<Integer, Map<AssetType, Result>> resultsMap
    ) {
        return !resultsMap.isEmpty();
    }

    private int getTotalResultCount(
        Map<Integer, Map<AssetType, Result>> resultsMap
    ) {
        return resultsMap.values().stream().mapToInt(Map::size).sum();
    }
}
