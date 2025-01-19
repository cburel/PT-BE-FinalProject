package game.catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import game.catalog.entity.CharacterConcept;

public interface CharacterConceptDao extends JpaRepository<CharacterConcept, Long>{
	
}
