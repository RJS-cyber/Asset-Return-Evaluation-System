package com.ares.backend.model;

import com.ares.backend.AssetType;

public class RawMaterials extends Asset {

    public RawMaterials(float startcapital, float interest, float volatility, float fluctuation) {
        super(startcapital, interest, volatility, fluctuation, AssetType.RAW_MATERIALS);
    }
}
