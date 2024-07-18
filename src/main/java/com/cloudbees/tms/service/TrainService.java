package com.cloudbees.tms.service;

import com.cloudbees.tms.dto.TrainDto;
import com.cloudbees.tms.entity.Train;
import com.cloudbees.tms.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainService {
    @Autowired
    private TrainRepository trainRepository;

    public Train createTrain(TrainDto trainDto) {
        Train train = Train.builder()
                .name(trainDto.getName())
                .code(trainDto.getCode())
                .departureTime(trainDto.getDepartureTime())
                .arrivalTime(trainDto.getArrivalTime()).build();
        return trainRepository.save(train);
    }

    public Train getTrainById(Long trainId) {
        return trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found with id: " + trainId));
    }

    public Optional<Train> getTrainByCode(String trainCode) {
        return trainRepository.findByCode(trainCode);
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Train updateTrain(Long trainId, TrainDto trainDto) {

        Train train = getTrainById(trainId);
        train.setName(trainDto.getName());
        train.setCode(trainDto.getCode());
        return trainRepository.save(train);
    }

    public void deleteTrain(Long trainId) {
        trainRepository.deleteById(trainId);
    }
}
