package com.prueba.pruebavirgin.dao;

import com.prueba.pruebavirgin.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaDao extends JpaRepository<Persona, Integer> {
}
