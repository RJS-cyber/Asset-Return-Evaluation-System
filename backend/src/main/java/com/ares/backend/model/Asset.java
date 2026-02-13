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
     * @param type Typ der Anlage
     * @param startcapital Startkapital der Anlage
     * @param years Anzahl der Jahre, die die Anlage gehalten wird
     * @param interest Rendite der Anlage
     * @param volatility Volatilität der Anlage
     */

    protected Asset(AssetType type, float startcapital, int years, float interest, float volatility) {
        this.type = type;
        this.startcapital = startcapital;
        this.years = years;
        this.interest = interest;
        this.volatility = volatility;
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
