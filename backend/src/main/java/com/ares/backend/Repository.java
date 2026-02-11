package com.ares.backend;

import com.ares.backend.model.Asset;
import com.ares.backend.model.Bonds;
import com.ares.backend.model.RawMaterials;
import com.ares.backend.model.RealEstates;
import com.ares.backend.model.Stocks;

import java.util.*;

public class Repository {
    private final List<Asset> assets = new ArrayList<>();

    public List<Asset> getAssets() {
        return assets;
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public void createAssetByType(AssetType type, float startcapital, float interest, float volatility, float fluctuation) {
        switch(type) {
            case BONDS -> addAsset(new Bonds(startcapital, interest, volatility, fluctuation));
            case RAW_MATERIALS -> addAsset(new RawMaterials(startcapital, interest, volatility, fluctuation));
            case REAL_ESTATES -> addAsset(new RealEstates(startcapital, interest, volatility, fluctuation));
            case STOCKS -> addAsset(new Stocks(startcapital, interest, volatility, fluctuation));
            default -> throw new IllegalArgumentException("Unknown AssetType: " + type);
        }
    }
}
