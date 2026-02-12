package com.ares.backend.model;

import com.ares.backend.AssetType;

public class Stocks extends Asset {

    public Stocks(float startcapital, int years) {
        super(startcapital, years, 8.0f, 15.0f, AssetType.STOCKS);
    }
}
