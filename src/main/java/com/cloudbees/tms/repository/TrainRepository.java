package com.cloudbees.tms.repository;

import com.cloudbees.tms.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

    public Optional<Train> findByCode(String code);
}
