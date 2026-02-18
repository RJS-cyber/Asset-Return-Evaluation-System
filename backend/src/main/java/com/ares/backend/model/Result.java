package com.ares.backend.model;

import com.ares.backend.AssetType;
import lombok.Getter;

public record Result(@Getter AssetType type, @Getter int year, @Getter double capital, @Getter double development) {

}
