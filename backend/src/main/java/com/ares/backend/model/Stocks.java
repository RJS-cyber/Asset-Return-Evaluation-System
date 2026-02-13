package com.ares.backend.model;

import com.ares.backend.AssetType;

public class Stocks extends Asset {

    public Stocks(float startcapital, int years) {
        super(AssetType.STOCKS, startcapital, years, 0.08f, 0.15f);
    }
}
