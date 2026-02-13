package com.ares.backend;

import com.ares.backend.model.Asset;
import com.ares.backend.model.Bonds;
import com.ares.backend.model.RawMaterials;
import com.ares.backend.model.RealEstates;
import com.ares.backend.model.Result;
import com.ares.backend.model.Stocks;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class Repository {

    @Getter
    private final List<Asset> assets = new ArrayList<>();

    // Nested Map: Year → (AssetType → Result)
    private final Map<Integer, Map<AssetType, Result>> resultsMap = new HashMap<>();


    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    private int amountOfTypes() {
        return getAssets().size();
    }

    public void storeResult(Result result) {
        int year = result.getYear();
        AssetType type = result.getType();

        // Get or create map for this year
        resultsMap.computeIfAbsent(year, key -> new HashMap<>())
                .put(type, result);
    }

    public Result getResultForYearAndType(int year, AssetType type) {
        return resultsMap.getOrDefault(year, Collections.emptyMap()).get(type);
    }

    public boolean hasResult(int year, AssetType type) {
        return resultsMap.containsKey(year) &&
                resultsMap.get(year).containsKey(type);
    }

    public Map<AssetType, Result> getResultsForYear(int year) {
        return resultsMap.getOrDefault(year, Collections.emptyMap());
    }

    public List<Result> getResultsForYearAsList(int year) {
        return new ArrayList<>(getResultsForYear(year).values());
    }

    public List<Integer> getAvailableYears() {
        return resultsMap.keySet().stream().sorted().collect(Collectors.toList());
    }

    /**
     * #Discard
     * Get results for multiple years
     */
    public Map<Integer, Map<AssetType, Result>> getResultsForYears(int... years) {
        Map<Integer, Map<AssetType, Result>> resultMap = new HashMap<>();
        for (int year : years) {
            if (resultsMap.containsKey(year)) {
                resultMap.put(year, resultsMap.get(year));
            }
        }
        return resultMap;
    }

    /**
     * Get results for a year range (inclusive)
     */
    public Map<Integer, Map<AssetType, Result>> getResultsForYearRange(int startYear, int endYear) {
        return resultsMap.entrySet().stream()
                .filter(entry -> entry.getKey() >= startYear && entry.getKey() <= endYear)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Get results for a specific asset type as a map (Year → Result)
     */
    public Map<Integer, Result> getResultsForAssetType(AssetType type) {
        return resultsMap.entrySet().stream()
                .filter(entry -> entry.getValue().containsKey(type))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().get(type)
                ));
    }

    public List<Result> getResultsForAssetTypeAsList(AssetType type) {
        return new ArrayList<>(getResultsForAssetType(type).values());
    }

    /**
     * #Discard
     * Get results for multiple asset types
     */
    public Map<AssetType, List<Result>> getResultsForAssetTypes(AssetType... types) {
        Map<AssetType, List<Result>> resultMap = new HashMap<>();
        for (AssetType type : types) {
            resultMap.put(type, getResultsForAssetTypeAsList(type));
        }
        return resultMap;
    }

    /**
     * Get all results as a flat list
     */
    public List<Result> getAllResultsAsList() {
        return resultsMap.values().stream()
                .flatMap(map -> map.values().stream())
                .sorted(Comparator.comparing(Result::getYear)
                        .thenComparing(r -> r.getType().getIndexId()))
                .collect(Collectors.toList());
    }

    /**
     * Find result with maximum capital
     */
    public Optional<Result> findMaxCapital() {
        return getAllResultsAsList().stream()
                .max(Comparator.comparing(Result::getCapital));
    }

    /**
     * Find result with maximum capital for specific asset type
     */
    public Optional<Result> findMaxCapitalForType(AssetType type) {
        return getResultsForAssetTypeAsList(type).stream()
                .max(Comparator.comparing(Result::getCapital));
    }

    /**
     * Find results with capital above threshold
     */
    public List<Result> findResultsAboveCapital(float threshold) {
        return getAllResultsAsList().stream()
                .filter(r -> r.getCapital() > threshold)
                .collect(Collectors.toList());
    }

    /**
     * Get total capital across all results for a specific year
     */
    public float getTotalCapitalForYear(int year) {
        return getResultsForYear(year).values().stream()
                .map(Result::getCapital)
                .reduce(0f, Float::sum);
    }

    /**
     * Get average development for a specific asset type
     */
    public float getAverageDevelopmentForType(AssetType type) {
        List<Result> typeResults = getResultsForAssetTypeAsList(type);
        if (typeResults.isEmpty()) return 0f;

        return (float) typeResults.stream()
                .mapToDouble(Result::getDevelopment)
                .average()
                .orElse(0.0);
    }

    /**
     * Get total number of stored results
     */
    public int getTotalResultCount() {
        return resultsMap.values().stream()
                .mapToInt(Map::size)
                .sum();
    }

    public int getYearCount() {
        return resultsMap.size();
    }

    public boolean hasResults() {
        return !resultsMap.isEmpty();
    }

    public void clearResults() {
        resultsMap.clear();
    }

    /**
     * #Discard
     * Store all results for a specific year
     */
    public void storeResultsForYear(int year, Map<AssetType, Result> yearResults) {
        resultsMap.put(year, new HashMap<>(yearResults));
    }

    /**
     * Get the raw nested map
     */
    public Map<Integer, Map<AssetType, Result>> getAllResultsRaw() {
        return Collections.unmodifiableMap(resultsMap);
    }
}