package com.ares.backend.model;

import com.ares.backend.AssetType;

public class RealEstates extends Asset {

    public RealEstates(float startcapital, int years) {
        super(AssetType.REAL_ESTATES, startcapital, years, 0.05f, 0.07f);
    }
}
