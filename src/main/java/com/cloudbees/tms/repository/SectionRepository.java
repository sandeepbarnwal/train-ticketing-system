package com.cloudbees.tms.repository;

import com.cloudbees.tms.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    public Optional<Section> findByName (String name);
}
