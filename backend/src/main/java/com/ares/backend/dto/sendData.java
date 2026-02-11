package com.ares.backend.dto;

import com.ares.backend.AssetType;
import lombok.Data;

import java.util.ArrayList;

@Data
public class sendData {
    private int years;
    private float amount;
    private ArrayList<AssetType> assetTypes;
}
