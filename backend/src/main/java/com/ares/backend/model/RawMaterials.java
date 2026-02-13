package com.ares.backend.model;

import com.ares.backend.AssetType;

public class RawMaterials extends Asset {

    public RawMaterials(float startcapital, int years) {
        super(AssetType.RAW_MATERIALS, startcapital, years, 0.06f, 0.10f);
    }
}
