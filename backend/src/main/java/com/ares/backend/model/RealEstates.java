package com.ares.backend.model;

import com.ares.backend.AssetType;

public class RealEstates extends Asset {

    public RealEstates(float startcapital, int years) {
        super(startcapital, years, 5.0f, 7.0f, AssetType.REAL_ESTATES);
    }
}
