package com.ares.backend;

import com.ares.backend.model.Asset;
import com.ares.backend.model.Bonds;
import com.ares.backend.model.RawMaterials;
import com.ares.backend.model.RealEstates;
import com.ares.backend.model.Result;
import com.ares.backend.model.Stocks;

import java.util.List;
import java.util.Random;

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
            List<Asset> assets = repository.getAssets();
            if (assets.isEmpty()) {
                throw new RuntimeException("No assets found in the repository");
            }

            Random random = new Random();
            int years = assets.get(0).getYears();

            // Write initial results for year 0
            for (Asset asset : assets) {
                Result initialResult = new Result(asset.getType(), 0, asset.getStartcapital(), 0.0f);
                repository.storeResult(initialResult);
            }

            // Calculate results for each subsequent year
            for (int year = 1; year <= years; year++) {
                for (Asset asset : assets) {
                    Result previousResult = repository.getResultForYearAndType(year - 1, asset.getType());
                    if (previousResult == null) {
                        throw new RuntimeException("Previous result not found for " + asset.getType() + " at year " + (year - 1));
                    }

                    Result newResult = calculation(previousResult, asset, year, random);
                    repository.storeResult(newResult);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Simulation error: " + e.getMessage());
        }
    }

    /**
     * @param previousResult The result from the previous year
     * @param asset The asset being calculated
     * @param year The current year
     * @param random Random number generator for Gaussian distribution
     * @return A new Result with calculated capital and development
     */
    private Result calculation(Result previousResult, Asset asset, int year, Random random) {
        float K0 = previousResult.getCapital();
        float r = asset.getInterest();
        float o = asset.getVolatility();
        float z = (float) random.nextGaussian();

        float Kn = K0 * (1 + (r + o * z));
        
        float development = Kn - K0;

        return new Result(asset.getType(), year, Kn, development);
    }

    public Result[][] getResults() {
        return repository.getResultsAsArray();
    }
}