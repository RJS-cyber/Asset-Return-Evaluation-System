package com.ares.backend.model;

import com.ares.backend.AssetType;

public abstract class Asset {
    private final float startcapital;
    private final int years;
    private final float interest;
    private final float volatility;
    private final AssetType type;

    /**
     * Konstruktor für die Anlageklasse.
     * @param startcapital Startkapital der Anlage
     * @param years Anzahl der Jahre, die die Anlage gehalten wird
     * @param interest Rendite der Anlage
     * @param volatility Volatilität der Anlage
     * @param type Typ der Anlage
     */

    protected Asset(float startcapital, int years, float interest, float volatility, AssetType type) {
        this.startcapital = startcapital;
        this.years = years;
        this.interest = interest;
        this.volatility = volatility;
        this.type = type;
    }

    public float getStartcapital() {
        return startcapital;
    }

    public int getYears() {
        return years;
    }

    public float getInterest() {
        return interest;
    }

    public float getVolatility() {
        return volatility;
    }

    public AssetType getType() {
        return type;
    }
}
