package com.ares.backend;

import com.ares.backend.model.Asset;
import com.ares.backend.model.Bonds;
import com.ares.backend.model.RawMaterials;
import com.ares.backend.model.RealEstates;
import com.ares.backend.model.Result;
import com.ares.backend.model.Stocks;

import java.util.List;

public class Service {

    private final Repository repository = new Repository();

    public List<Asset> getAssets() {
        if (repository.getAssets().isEmpty()) {
            throw new RuntimeException("No assets found in the repository");
        }
        return repository.getAssets();
    }

    public int getYears() {
        if (repository.getAssets().isEmpty()) {
            throw new RuntimeException("No assets found in the repository");
        }
        return repository.getAssets().get(0).getYears();
    }

    public void storeData(int years, float amount, List<AssetType> assetTypes) {
        try {
            if (assetTypes == null || assetTypes.isEmpty()) {
                throw new IllegalArgumentException(
                    "Asset types cannot be null or empty"
                );
            }
            if (amount <= 0) {
                throw new IllegalArgumentException(
                    "Amount must be greater than zero"
                );
            }
            if (years <= 0) {
                throw new IllegalArgumentException(
                    "Years must be greater than zero"
                );
            }

            for (AssetType assetType : assetTypes) {
                Asset asset = createAssetByType(assetType, amount, years);
                repository.addAsset(asset);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private Asset createAssetByType(AssetType type, float startcapital, int years) {
        return switch(type) {
            case BONDS -> new Bonds(startcapital, years);
            case RAW_MATERIALS -> new RawMaterials(startcapital, years);
            case REAL_ESTATES -> new RealEstates(startcapital, years);
            case STOCKS -> new Stocks(startcapital, years);
            default -> throw new IllegalArgumentException("Unknown AssetType: " + type);
        };
    }

    public void createResult(AssetType type, int year, float capital, float development) {
        Result result = new Result(type, year, capital, development);
        repository.storeResult(result);
    }

    public void simulation() {
        try {
            for (Asset asset : getAssets()) {
                Result initialResult = new Result(asset.getType(), 0, asset.getStartcapital(), 0);
                repository.storeResult(initialResult);
            }
            for (int i = 0; i < getYears(); i++) {
                for (Asset asset : getAssets()) {

                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Simulation error: " + e.getMessage());
        }
    }
}

/*
 * -- INPUT --
 * for years
 * for assets type
 * assets liegen vor als
 *   einzelne subklassen
 *   in einer liste im repo
 *
 * startcapital
 * years
 * interest
 * volat
 * type
 *
 *
 * -- OUTPUT --
 * result in ein array im repo
 * jedes years als neues array in der liste
 *
 * type
 * Years
 * capital
 * interest
 *
 * -- ABLAUF --
 *
 * erst die assets als results speichern als Jahr 0
 * one asset of array
 * new result (asset-type, i = 0, capital, development = 0)
 *
 *
 * Rechnung
 *
 * K0 = capital - result
 * r = interest - Asset
 * o = volat - Asset
 * z = flux - calc
 *
 * K1 = K0 * (1 + (r + o * z) )
 *
 * -- void calc(previous, asset) --
 * previous result
 * capital = previous-capital * (1 + (asset-interest + asset-volat * nextgaussian))
 * development = capital - previous
 *
 * result = repo.createresult (asset-type, i as year, capital, development)
 * repo.add result
 *
 * */
