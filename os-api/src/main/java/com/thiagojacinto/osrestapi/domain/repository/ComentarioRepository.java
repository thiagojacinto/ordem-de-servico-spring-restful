package com.thiagojacinto.osrestapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagojacinto.osrestapi.domain.models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
	
}
