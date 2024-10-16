package com.orielly.demo.dao;

import com.orielly.demo.entities.Officer;
import com.orielly.demo.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//This replaces the JpaOfficerDAO
public interface OfficerRepository extends JpaRepository<Officer, Integer> {
    List<Officer> findAllByLastNameContainingAndRank(String last, Rank rank);
}
