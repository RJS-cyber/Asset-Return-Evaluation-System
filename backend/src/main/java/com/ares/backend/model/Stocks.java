package com.ares.backend.model;

import com.ares.backend.AssetType;

public class Stocks extends Asset {

    public Stocks(float startcapital, int years) {
        super(AssetType.STOCKS, startcapital, years, 8.0f, 15.0f);
    }
}
