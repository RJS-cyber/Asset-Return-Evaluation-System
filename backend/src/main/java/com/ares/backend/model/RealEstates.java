package com.ares.backend.model;

import com.ares.backend.util.AssetType;

public class RealEstates extends Asset {

    public RealEstates(double startcapital, int years) {
        super(AssetType.REAL_ESTATES, startcapital, years, 0.06f, 0.04f);
    }
}
