package com.ares.backend.model;

import com.ares.backend.util.AssetType;

public class Bonds extends Asset {

    public Bonds(double startcapital, int years) {
        super(AssetType.BONDS, startcapital, years, 0.03f, 0.02f);
    }
}
