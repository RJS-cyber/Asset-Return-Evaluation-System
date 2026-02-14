package com.ares.backend.model;

import com.ares.backend.AssetType;

public class RawMaterials extends Asset {

    public RawMaterials(double startcapital, int years) {
        super(AssetType.RAW_MATERIALS, startcapital, years, 0.05f, 0.09f);
    }
}
