package com.ares.test;

import com.ares.backend.Systemrepository;
import com.ares.backend.model.Asset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssetTester {

    Systemrepository systemrepository = new Systemrepository();

    @Test
    void testCreateAssetByType(){
        systemrepository.createAssetByType(com.ares.backend.AssetType.BONDS, 1000f, 5f, 2f, 1f);
        assertEquals(1, systemrepository.getAssets().size());

        Asset bonds = systemrepository.getAssets().get(0);
        assertEquals(com.ares.backend.AssetType.BONDS, bonds.getType());
        assertEquals(1000f, bonds.getStartcapital());
        assertEquals(5f, bonds.getInterest());
        assertEquals(2f, bonds.getVolatility());
        assertEquals(1f, bonds.getFluctuation());

    }

    @Test
    void testCreateAssetByTypeInvalid(){
        assertThrows(IllegalArgumentException.class, () -> {
            systemrepository.createAssetByType(null, 1000f, 5f, 2f, 1f);
        });

        /* // Cannot define a new enum value at runtime in Java
        enum AssetType {INVALID};
        assertThrows(IllegalArgumentException.class, () -> {
            systemrepository.createAssetByType(AssetType.INVALID, 1000f, 5f, 2f, 1f);
        });
        */
    }
}
