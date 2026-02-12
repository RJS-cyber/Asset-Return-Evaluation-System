package com.ares.backend.model;

import com.ares.backend.AssetType;

public class Result {

    private final AssetType type;
    private final int year;
    private final float capital;
    private final float interest;

    public Result(
        AssetType type,
        int year,
        float capital,
        float evolution
    ) {
        this.type = type;
        this.year = year;
        this.capital = capital;
        this.interest = evolution;
    }

    public AssetType getType() {
        return this.type;
    }

    public int getYear() {
        return this.year;
    }

    public float getCapital() {
        return this.capital;
    }

    public float getInterest() {
        return this.interest;
    }
}
