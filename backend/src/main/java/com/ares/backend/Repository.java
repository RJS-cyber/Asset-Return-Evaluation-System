package com.ares.backend;

import com.ares.backend.model.Asset;
import com.ares.backend.model.Result;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class Repository {

    @Getter
    private final List<Asset> assets = new ArrayList<>();
    // Nested Map: Year -> (AssetType -> Result)
    private final Map<Integer, Map<AssetType, Result>> resultsMap = new HashMap<>();


    // ==============Storage==============
    // ====================================
    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public void storeResult(Result result) {
        int year = result.getYear();
        AssetType type = result.getType();

        resultsMap.computeIfAbsent(year, key -> new EnumMap<>(AssetType.class)).put(type, result);
    }

    public void clearResults() {
        resultsMap.clear();
    }

    // ===============Output===============
    // ====================================

    protected Result getResultForYearAndType(int year, AssetType type) {
        return resultsMap.getOrDefault(year, Collections.emptyMap()).get(type);
    }


    // After all results have been stored, convert the nested map to a 2D array for easier access in the frontend
    protected Result[][] getResultsAsArray() {
        int yearCount = getYearCount();
        int typeCount = amountOfTypes();
        Result[][] array = new Result[yearCount][typeCount];

        List<Integer> sortedYears = getAvailableYears();
        for (int i = 0; i < sortedYears.size(); i++) {
            int year = sortedYears.get(i);
            Map<AssetType, Result> yearResults = getResultsForYear(year);
            for (AssetType type : AssetType.values()) { //FIXME - assumes all Assets have been chosen
                array[i][type.getIndexId()] = yearResults.get(type);
            }
        }
        return array;
    }

    // Get the raw nested map
    public Map<Integer, Map<AssetType, Result>> getAllResultsRaw() {
        return Collections.unmodifiableMap(resultsMap);
    }


    // ==============Analysis==============
    // ====================================

    // Function for graph dimensions
    protected int getYearCount() {
        return resultsMap.size();
    }

    // Function for graph dimensions
    protected float getMaxCapital() {
        float maxCapital = 0f;
        for (Map<AssetType, Result> yearResults : resultsMap.values()) {
            for (Result result : yearResults.values()) {
                if (result.getCapital() > maxCapital) {
                    maxCapital = result.getCapital();
                }
            }
        }
        return maxCapital;
    }


    // Function for getResultsAsArray
    private List<Integer> getAvailableYears() {
        return resultsMap.keySet().stream().sorted().toList();
    }

    // Function for getResultsAsArray
    private Map<AssetType, Result> getResultsForYear(int year) {
        return resultsMap.getOrDefault(year, Collections.emptyMap());
    }

    // Function for getResultsAsArray
    private int amountOfTypes() {
        return getAssets().size();
    }




    // ===============Unused===============
    // ====================================
    protected Map<Integer, Result> getResultsForAssetType(AssetType type) {
        return resultsMap.entrySet().stream()
                .filter(entry -> entry.getValue().containsKey(type))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get(type)
                ));
    }

    protected List<Result> getResultsForAssetTypeAsList(AssetType type) {
        return new ArrayList<>(getResultsForAssetType(type).values());
    }

    /**
     * Get average development for a specific asset type
     */
    protected float getAverageDevelopmentForType(AssetType type) {
        List<Result> typeResults = getResultsForAssetTypeAsList(type);
        if (typeResults.isEmpty()) return 0f;

        return (float) typeResults.stream()
                .mapToDouble(Result::getDevelopment)
                .average()
                .orElse(0.0);
    }

    /**
     * Get results for multiple asset types
     */
    protected Map<AssetType, List<Result>> getResultsForAssetTypes(AssetType... types) {
        Map<AssetType, List<Result>> resultMap = new EnumMap<>(AssetType.class);
        for (AssetType type : types) {
            resultMap.put(type, getResultsForAssetTypeAsList(type));
        }
        return resultMap;
    }

}