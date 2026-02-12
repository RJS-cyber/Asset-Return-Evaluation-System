package com.ares.backend;

import com.ares.backend.model.Asset;

import java.util.List;

public class Service {
    private final Repository repository = new Repository();

    public void storeData(int years, float amount, List<AssetType> assetTypes) {

        try {
            if (assetTypes == null || assetTypes.isEmpty()) {
                throw new IllegalArgumentException("Asset types cannot be null or empty");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero");
            }
            if (years <= 0) {
                throw new IllegalArgumentException("Years must be greater than zero");
            }

            for (AssetType assetType : assetTypes) {
                repository.createAssetByType(assetType, amount, years);
            }

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Asset> getAssets() {
        return repository.getAssets();
    }





}
