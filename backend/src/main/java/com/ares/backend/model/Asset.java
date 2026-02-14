package com.ares.backend.model;

import com.ares.backend.AssetType;
import lombok.Getter;

public abstract class Asset {

    @Getter
    private final double startcapital;
    @Getter
    private final int years;
    @Getter
    private final float interest;
    @Getter
    private final float volatility;
    @Getter
    private final AssetType type;

    /**
     * Konstruktor für die Anlageklasse.
     * @param type Typ der Anlage
     * @param startcapital Startkapital der Anlage
     * @param years Anzahl der Jahre, die die Anlage gehalten wird
     * @param interest Rendite der Anlage
     * @param volatility Volatilität der Anlage
     */

    protected Asset(AssetType type, double startcapital, int years, float interest, float volatility) {
        this.type = type;
        this.startcapital = startcapital;
        this.years = years;
        this.interest = interest;
        this.volatility = volatility;
    }
}
