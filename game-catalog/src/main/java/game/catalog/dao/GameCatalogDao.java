package game.catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import game.catalog.entity.Game;

public interface GameCatalogDao extends JpaRepository<Game, Long>{
	
}
