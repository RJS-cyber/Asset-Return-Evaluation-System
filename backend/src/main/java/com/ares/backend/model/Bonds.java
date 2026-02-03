package com.ares.backend.model;

import com.ares.backend.AssetType;

public class Bonds extends Asset {

    public Bonds(float startcapital, float interest, float volatility, float fluctuation) {
        super(startcapital, interest, volatility, fluctuation, AssetType.BONDS);
    }
}
