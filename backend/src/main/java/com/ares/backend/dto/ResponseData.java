package com.ares.backend.dto;


import com.ares.backend.model.Result;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseData {
    private Result[][] resultsArray;
    private int maxYears;
    private double maxCapital;
    private List<String> assetTypes;
}
