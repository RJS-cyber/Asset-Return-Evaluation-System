package com.ares.backend;

import com.ares.backend.model.Asset;
import com.ares.backend.model.Result;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;

public class Repository {

    @Getter
    private final List<Asset> assets = new ArrayList<>();

    // Nested Map: Year -> (AssetType -> Result)
    private final Map<Integer, Map<AssetType, Result>> resultsMap =
        new HashMap<>();

    // ============= Storage =============
    // ====================================
    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public void storeResult(Result result) {
        int year = result.year();
        AssetType type = result.type();

        resultsMap
            .computeIfAbsent(year, key -> new EnumMap<>(AssetType.class))
            .put(type, result);
    }

    public void clearRepo() {
        assets.clear();
        resultsMap.clear();
    }

    // ============== Output ==============
    // ====================================

    protected Result getResultForYearAndType(int year, AssetType type) {
        return resultsMap.getOrDefault(year, Collections.emptyMap()).get(type);
    }

    // After all results have been stored, convert the nested map to a 2D array for easier access in the frontend
    protected Result[][] getResultsAsArray() {
        int yearCount = assets.get(0).getYears() + 1;
        int typeCount = amountOfTypes();
        Result[][] resultArray = new Result[yearCount][typeCount];

        for (int i = 0; i < yearCount; i++) {
            Map<AssetType, Result> yearResults = getResultsForYear(i);
            for(int j = 0; j < yearResults.size(); j++){
                resultArray[i][j] = yearResults.get(AssetType.values()[j]);
            }
        }
        return resultArray;
    }

    // Get the raw nested map - Maybe delete and place into the test itself
    public Map<Integer, Map<AssetType, Result>> getAllResultsRaw() {
        return Collections.unmodifiableMap(resultsMap);
    }

    // ============= Analysis =============
    // ====================================

    // Function for graph dimensions
    protected double getMaxCapital() {
        double maxCapital = 0f;
        for (Map<AssetType, Result> yearResults : resultsMap.values()) {
            for (Result result : yearResults.values()) {
                if (result.capital() > maxCapital) {
                    maxCapital = result.capital();
                }
            }
        }
        return maxCapital;
    }

    // Function for getResultsAsArray
    private Map<AssetType, Result> getResultsForYear(int year) {
        return resultsMap.getOrDefault(year, Collections.emptyMap());
    }

    // Function for getResultsAsArray
    private int amountOfTypes() {
        return getAssets().size();
    }
}
