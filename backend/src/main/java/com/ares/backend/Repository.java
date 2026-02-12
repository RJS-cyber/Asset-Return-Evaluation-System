package com.ares.backend;

import com.ares.backend.model.Asset;
import com.ares.backend.model.Bonds;
import com.ares.backend.model.RawMaterials;
import com.ares.backend.model.RealEstates;
import com.ares.backend.model.Result;
import com.ares.backend.model.Stocks;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private final int maxTypes = AssetType.getMaxTyes();
    private final List<Asset> assets = new ArrayList<>();
    private final List<Result[]> results = new ArrayList<>();


    public List<Asset> getAssets() {
        return assets;
    }

    public List<Result[]> getResults() {
        return results;
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public void addResult(Result result) {
        if (results.isEmpty()) {
            results.add(new Result[maxTypes + 1]);
        } else {
            Result[] currentResults = results.get(results.size() - 1);
            Result existingResult = currentResults[result.getType().getIndexId()];

            if (existingResult != null && existingResult.getYear() == result.getYear() - 1) {
                results.add(new Result[maxTypes + 1]);
            }
        }

        Result[] currentResults = results.get(results.size() - 1);
        addResultToResultArray(currentResults, result);
    }

    private void addResultToResultArray(Result[] resultArray, Result result) {
        AssetType type = result.getType();
        int typeIndex = type.getIndexId();

        resultArray[typeIndex] = result;
    }


    public void createAssetByType(AssetType type, float startcapital, int years) {
        switch(type) {
            case BONDS -> addAsset(new Bonds(startcapital, years));
            case RAW_MATERIALS -> addAsset(new RawMaterials(startcapital, years));
            case REAL_ESTATES -> addAsset(new RealEstates(startcapital, years));
            case STOCKS -> addAsset(new Stocks(startcapital, years));
            default -> throw new IllegalArgumentException("Unknown AssetType: " + type);
        }
    }

    public void createResult(AssetType type, int year, float capital, float interest) {
        addResult(new Result(type, year, capital, interest));
    }
}
