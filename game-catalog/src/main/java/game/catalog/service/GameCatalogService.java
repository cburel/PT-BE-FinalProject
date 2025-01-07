package game.catalog.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import game.catalog.controller.model.GameCatalogData;
import game.catalog.dao.GameCatalogDao;
import game.catalog.entity.Game;

@Service
public class GameCatalogService {
	@Autowired
	private GameCatalogDao gameCatalogDao;

	@Transactional(readOnly = false)
	public GameCatalogData saveGame(GameCatalogData gameCatalogData) {
		Long gameId = gameCatalogData.getGameId();
		Game game = findOrCreateGame(gameId);
		
		copyGameFields(game, gameCatalogData);
		return new GameCatalogData(gameCatalogDao.save(game));
	}
	
	private void copyGameFields(Game game, GameCatalogData gameCatalogData) {
		game.setGameDescription(gameCatalogData.getGameDescription());
		game.setGameId(gameCatalogData.getGameId());
		game.setGameReleaseDate(gameCatalogData.getGameReleaseDate());
		game.setGameTitle(gameCatalogData.getGameTitle());
	}
	
	private Game findOrCreateGame(Long gameId) {
		if(Objects.isNull(gameId)) {
			return new Game();
		}
		else {
			return findGameById(gameId);
		}
	}
	
	private Game findGameById(Long gameId) {
		return gameCatalogDao.findById(gameId)
				.orElseThrow(() -> new NoSuchElementException (
						"Game with ID=" + gameId + " was not found.") );
	}
}
