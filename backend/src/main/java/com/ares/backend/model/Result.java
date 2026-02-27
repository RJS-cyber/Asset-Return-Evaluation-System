package com.ares.backend.model;

import com.ares.backend.AssetType;

public record Result(AssetType type, int year, double capital, double development) {

}
