package game.catalog.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import game.catalog.controller.model.GameCatalogData;
import game.catalog.controller.model.GameCatalogData.GameCatalogMechanic;
import game.catalog.dao.GameCatalogDao;
import game.catalog.dao.MechanicDao;
import game.catalog.entity.Game;
import game.catalog.entity.Mechanic;

@Service
public class GameCatalogService {
	//@Autowired
	private GameCatalogDao gameCatalogDao;
	// constructor avoids autowiring for easier testing
	public GameCatalogService(GameCatalogDao dao) {
		this.gameCatalogDao = dao;
	}

	private MechanicDao mechanicDao;
	public GameCatalogService(MechanicDao mechDao) {
		this.mechanicDao = mechDao;
	}
	
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
	
	public List<Game> getAllGames() {
		List<Game> games = gameCatalogDao.findAll();
		return games;
	}

	@Transactional(readOnly = true)
	public GameCatalogData getGameById(Long gameId) {
		return new GameCatalogData(findGameById(gameId));	
	}

	@Transactional(readOnly = false)
	public GameCatalogMechanic saveMechanic(Long gameId, GameCatalogMechanic mechanic) {
		Game game = findGameById(gameId);
		Long mechanicId = mechanic.getMechanicId();
		Mechanic mech = findOrCreateMechanic(gameId, mechanicId);
		
		copyMechanicFields(mech, mechanic);
		
		mech.getGames().add(game);
		
		Mechanic dbMechanic = mechanicDao.save(mech);
	}
	
	public Mechanic findMechanicById(Long gameId, Long mechanicId) {
		Mechanic mechanic = mechanicDao.findById(mechanicId)
				.orElseThrow(() -> new NoSuchElementException("Mechanic with ID=" + mechanicId + " was not found."));
		
		boolean found = false;
		
		for(Game game : mechanic.getGames()) {
			if(game.getGameId() == gameId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("The mechanic with ID=" + mechanicId + " does not belong to game with ID=" + gameId);
		}
		
		return mechanic;
	}
}
