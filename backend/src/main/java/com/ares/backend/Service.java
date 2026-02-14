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
    Random random = new Random();

    // ========== Handle Inputs ==========
    // ====================================
    public void storeData(int years, double amount, List<AssetType> assetTypes) {
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

            repository.getAssets().clear();
            repository.clearResults();

            for (AssetType assetType : assetTypes) {
                Asset asset = createAssetByType(assetType, amount, years);
                repository.addAsset(asset);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private Asset createAssetByType(AssetType type, double startcapital, int years) {
        return switch(type) {
            case BONDS -> new Bonds(startcapital, years);
            case RAW_MATERIALS -> new RawMaterials(startcapital, years);
            case REAL_ESTATES -> new RealEstates(startcapital, years);
            case STOCKS -> new Stocks(startcapital, years);
            default -> throw new IllegalArgumentException("Unknown AssetType: " + type);
        };
    }

    // ========= Calculate Results =========
    // ====================================
    public void simulation() {
        try {
            List<Asset> assets = repository.getAssets();
            if (assets.isEmpty()) {
                throw new RuntimeException("No assets found in the repository");
            }


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
        double previousCapital = previousResult.capital();
        float interest = asset.getInterest();
        float volatility = asset.getVolatility();
        float fluxiation = (float) random.nextGaussian();

        double currentCapital = previousCapital * (1 + (interest + volatility * fluxiation));

        double development = currentCapital - previousCapital;

        return new Result(asset.getType(), year, currentCapital, development);
    }


    // ========= Send to Frontend =========
    // ====================================
    public Result[][] getResults() {
        return repository.getResultsAsArray();
    }

    public int graphDataYears() {
        return getResults().length;
    }

    public double graphDataMaxCapital() {
        return repository.getMaxCapital();
    }
}