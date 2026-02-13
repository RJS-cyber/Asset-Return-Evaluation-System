package com.ares.backend.model;

import com.ares.backend.AssetType;

public class Bonds extends Asset {

    public Bonds(float startcapital, int years) {
        super(AssetType.BONDS, startcapital, years, 0.03f, 0.05f);
    }
}
