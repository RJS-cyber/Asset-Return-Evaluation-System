package com.ares.backend.dto;

import com.ares.backend.AssetType;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SendData {
    private int years;
    private double amount;
    private ArrayList<AssetType> assetTypes;
}
