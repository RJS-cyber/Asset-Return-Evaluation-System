package com.ares.backend;

import com.ares.backend.model.Asset;

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

    public void simulation() {
        try {
            for (int i = 0; i < getYears(); i++) {

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
*
* -- OUTPUT --
* result in ein array im repo
* jedes years als neues array in der liste
*
*
* -- ABLAUF --
* erst die assets als results speichern als Jahr 0
*
* Rechnung
* werte aus dem Vorjahr nehmen
*
*
* */