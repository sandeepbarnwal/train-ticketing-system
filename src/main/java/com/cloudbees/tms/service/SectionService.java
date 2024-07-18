package com.cloudbees.tms.service;

import com.cloudbees.tms.dto.SectionDto;
import com.cloudbees.tms.entity.Section;
import com.cloudbees.tms.entity.Train;
import com.cloudbees.tms.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    TrainService trainService;
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Section getSectionById(Long sectionId) {
        return sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Train not found with id: " + sectionId));
    }

    public Section createSection(SectionDto sectionDto) {
        Train train = trainService.getTrainById(sectionDto.getTrainId());
        Section section = Section.builder()
                .name(sectionDto.getName())
                .train(train)
                .build();
        return sectionRepository.save(section);
    }

    public Section updateSection(Long sectionId, SectionDto sectionDto) {
        Section section = getSectionById(sectionId);
        section.setName(sectionDto.getName());
        Train train = trainService.getTrainById(sectionDto.getTrainId());
        section.setTrain(train);
        return sectionRepository.save(section);
    }

    public void deleteSection(Long sectionId) {
         sectionRepository.deleteById(sectionId);
    }

    public Optional<Section> findBySectionName(String sectionName) {
        return sectionRepository.findByName(sectionName);
    }
}
