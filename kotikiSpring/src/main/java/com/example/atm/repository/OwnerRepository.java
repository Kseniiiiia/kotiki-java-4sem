package com.example.atm.repository;

import com.example.atm.model.Owner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<Owner> findByOwnerName(String name);
}

