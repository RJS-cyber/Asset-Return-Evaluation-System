package com.ares.backend;

import com.ares.backend.model.Asset;
import com.ares.backend.model.Bonds;
import com.ares.backend.model.RawMaterials;
import com.ares.backend.model.RealEstates;
import com.ares.backend.model.Result;
import com.ares.backend.model.Stocks;
import java.util.*;

public class Repository {

    private final List<Asset> assets = new ArrayList<>();
    private final List<Result> results = new ArrayList<>();

    public List<Asset> getAssets() {
        return assets;
    }

    public List<Result> getResult() {
        return results;
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public void addResult(Result result) {
        results.add(result);
    }

    public void createAssetByType(
        AssetType type,
        float startcapital,
        float interest,
        float volatility,
        float fluctuation
    ) {
        switch (type) {
            case BONDS -> addAsset(
                new Bonds(startcapital, interest, volatility, fluctuation)
            );
            case RAW_MATERIALS -> addAsset(
                new RawMaterials(startcapital, interest, volatility, fluctuation)
            );
            case REAL_ESTATES -> addAsset(
                new RealEstates(startcapital, interest, volatility, fluctuation)
            );
            case STOCKS -> addAsset(
                new Stocks(startcapital, interest, volatility, fluctuation)
            );
            default -> throw new IllegalArgumentException(
                "Unknown AssetType: " + type
            );
        }
    }

    public void createResult(AssetType type, int year, float capital, float interest) {
        addResult(new Result(type, year, capital, interest))
    }
}
