package com.ares.backend;

import com.ares.backend.dto.ResponseData;
import com.ares.backend.dto.SendData;
import com.ares.backend.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ares")
@CrossOrigin(origins = "*")
public class Controller {

    private final Service service;

    public Controller() {
        this.service = new Service();
    }

    @PostMapping("/calculate")
    public ResponseEntity<ResponseData> calculate(@RequestBody SendData data) {
        try {
            if (data.getAssetTypes() == null || data.getAssetTypes().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            if (data.getAmount() <= 0 || data.getYears() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            service.storeData(data.getYears(), data.getAmount(), data.getAssetTypes());
            service.simulation();

            Result[][] resultsArray = service.getResults();
            int maxYears = service.graphDataYears() - 1;
            double maxCapital = service.graphDataMaxCapital();
            List<String> assetTypes = data.getAssetTypes().stream()
                    .map(AssetType::name)
                    .toList();

            ResponseData response = new ResponseData(
                    resultsArray,
                    maxYears,
                    maxCapital,
                    assetTypes
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
