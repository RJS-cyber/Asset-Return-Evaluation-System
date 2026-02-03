package com.ares.backend.model;

import com.ares.backend.AssetType;

public class Stocks extends Asset {

    public Stocks(float startcapital, float interest, float volatility, float fluctuation) {
        super(startcapital, interest, volatility, fluctuation, AssetType.STOCKS);
    }
}
