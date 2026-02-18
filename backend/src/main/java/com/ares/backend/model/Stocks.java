package com.ares.backend.model;

import com.ares.backend.util.AssetType;

public class Stocks extends Asset {

    public Stocks(double startcapital, int years) {
        super(AssetType.STOCKS, startcapital, years, 0.08f, 0.12f);
    }
}
