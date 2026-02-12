package com.ares.backend.model;

public class Result {

    private final AssetType type;
    private final int year;
    private final float capital;
    private final float interest;

    public private Result(
        AssetType type,
        int year,
        float capital,
        float interest
    ) {
        type = type;
        year = year;
        capital = capital;
        interest = interest;
    }
}
