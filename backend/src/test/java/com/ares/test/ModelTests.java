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
