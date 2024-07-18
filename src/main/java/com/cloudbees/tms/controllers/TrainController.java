package com.cloudbees.tms.controllers;

import com.cloudbees.tms.dto.TrainDto;
import com.cloudbees.tms.entity.Train;
import com.cloudbees.tms.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {
    @Autowired
    private TrainService trainService;

    @GetMapping
    public List<Train> getAllTrains() {
        return trainService.getAllTrains();
    }

    @GetMapping("/{trainId}")
    public Train getTrainById(@PathVariable Long trainId) {
        return trainService.getTrainById(trainId);
    }

    @PostMapping
    public Train createTrain(@RequestBody TrainDto trainDto) {
        return trainService.createTrain(trainDto);
    }

    @PutMapping("/{trainId}")
    public Train updateTrain(@PathVariable Long trainId, @RequestBody TrainDto train) {

        return trainService.updateTrain(trainId, train);
    }

    @DeleteMapping("/{trainId}")
    public void deleteTrain(@PathVariable Long trainId) {
        trainService.deleteTrain(trainId);
    }
}
