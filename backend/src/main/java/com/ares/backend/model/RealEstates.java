package com.ares.backend.model;

import com.ares.backend.AssetType;

public class RealEstates extends Asset {

    public RealEstates(float startcapital, float interest, float volatility, float fluctuation) {
        super(startcapital, interest, volatility, fluctuation, AssetType.REAL_ESTATES);
    }
}
