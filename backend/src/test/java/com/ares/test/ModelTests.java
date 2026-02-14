package com.ares.test;

import com.ares.backend.AssetType;
import com.ares.backend.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelTests {

    @Test
    void testBonds_Creation() {
        Bonds bonds = new Bonds(1000f, 5);
        assertEquals(1000f, bonds.getStartcapital());
        assertEquals(5, bonds.getYears());
        assertEquals(0.03f, bonds.getInterest());
        assertEquals(0.05f, bonds.getVolatility());
        assertEquals(AssetType.BONDS, bonds.getType());
    }

    @Test
    void testStocks_Creation() {
        Stocks stocks = new Stocks(2000f, 10);
        assertEquals(2000f, stocks.getStartcapital());
        assertEquals(10, stocks.getYears());
        assertEquals(0.08f, stocks.getInterest());
        assertEquals(0.15f, stocks.getVolatility());
        assertEquals(AssetType.STOCKS, stocks.getType());
    }

    @Test
    void testRealEstates_Creation() {
        RealEstates realEstates = new RealEstates(50000f, 20);
        assertEquals(50000f, realEstates.getStartcapital());
        assertEquals(20, realEstates.getYears());
        assertEquals(0.05f, realEstates.getInterest());
        assertEquals(0.07f, realEstates.getVolatility());
        assertEquals(AssetType.REAL_ESTATES, realEstates.getType());
    }

    @Test
    void testRawMaterials_Creation() {
        RawMaterials rawMaterials = new RawMaterials(3000f, 15);
        assertEquals(3000f, rawMaterials.getStartcapital());
        assertEquals(15, rawMaterials.getYears());
        assertEquals(0.06f, rawMaterials.getInterest());
        assertEquals(0.1f, rawMaterials.getVolatility());
        assertEquals(AssetType.RAW_MATERIALS, rawMaterials.getType());
    }

    @Test
    void testResult_Creation() {
        Result result = new Result(AssetType.BONDS, 5, 1500f, 3.5f);
        assertEquals(AssetType.BONDS, result.type());
        assertEquals(5, result.year());
        assertEquals(1500f, result.capital());
        assertEquals(3.5f, result.development());
    }

    @Test
    void testResult_AllGetters() {
        Result result = new Result(AssetType.STOCKS, 10, 5000f, 8.2f);
        assertNotNull(result.type());
        assertTrue(result.year() > 0);
        assertTrue(result.capital() > 0);
        assertTrue(result.development() > 0);
    }
}
