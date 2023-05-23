package com.performance360.performance360.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.performance360.performance360.model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{
	public List<Disciplina> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
}
