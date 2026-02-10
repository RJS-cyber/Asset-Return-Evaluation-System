package com.ares.test;

import com.ares.backend.Repository;
import com.ares.backend.model.Asset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssetTester {

    Repository repository = new Repository();

    @Test
    void testCreateAssetByType(){
        repository.createAssetByType(com.ares.backend.AssetType.BONDS, 1000f, 5f, 2f, 1f);
        assertEquals(1, repository.getAssets().size());

        Asset bonds = repository.getAssets().get(0);
        assertEquals(com.ares.backend.AssetType.BONDS, bonds.getType());
        assertEquals(1000f, bonds.getStartcapital());
        assertEquals(5f, bonds.getInterest());
        assertEquals(2f, bonds.getVolatility());
        assertEquals(1f, bonds.getFluctuation());

    }

    @Test
    void testCreateAssetByTypeInvalid(){
        assertThrows(NullPointerException.class, () ->{
            repository.createAssetByType(null, 1000f, 5f, 2f, 1f);
        });
    }
}
