package com.ares.backend.model;

import com.ares.backend.AssetType;

public class RawMaterials extends Asset {

    public RawMaterials(float startcapital, int years) {
        super(startcapital, years, 6.0f, 10.0f, AssetType.RAW_MATERIALS);
    }
}
