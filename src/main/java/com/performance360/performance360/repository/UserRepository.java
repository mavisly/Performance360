package com.performance360.performance360.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.performance360.performance360.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByNickname(String nickname);
	
	public List<User> findAllByNomeContainingIgnoreCase(@Param("nome")String nome);
}
