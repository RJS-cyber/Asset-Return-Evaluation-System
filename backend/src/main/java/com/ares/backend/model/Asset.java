package com.ares.backend.model;

import com.ares.backend.AssetType;

public abstract class Asset {

    private final float startcapital;
    private final float interest;
    private final float volatility;
    private final float fluctuation;
    private final AssetType type;

    /**
     * Konstruktor für die Anlageklasse.
     * @param startcapital Startkapital der Anlage
     * @param interest Rendite der Anlage
     * @param volatility Volatilität der Anlage
     * @param fluctuation Zufallsschwankung der Anlage
     * @param type Typ der Anlage
     */

    protected Asset(
        float startcapital,
        float interest,
        float volatility,
        float fluctuation,
        AssetType type
    ) {
        this.startcapital = startcapital;
        this.interest = interest;
        this.volatility = volatility;
        this.fluctuation = fluctuation;
        this.type = type;
    }

    public float getStartcapital() {
        return startcapital;
    }

    public float getInterest() {
        return interest;
    }

    public float getVolatility() {
        return volatility;
    }

    public float getFluctuation() {
        return fluctuation;
    }

    public AssetType getType() {
        return type;
    }
}
