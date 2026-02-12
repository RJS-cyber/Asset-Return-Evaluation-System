package com.ares.backend.model;

import com.ares.backend.AssetType;

public class Bonds extends Asset {

    public Bonds(float startcapital, int years) {
        super(startcapital, years, 3.0f, 5.0f, AssetType.BONDS);
    }
}
