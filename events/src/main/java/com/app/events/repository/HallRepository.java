package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.events.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {

}