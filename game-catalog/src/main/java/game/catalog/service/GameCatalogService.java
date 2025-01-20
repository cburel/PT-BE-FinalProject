package game.catalog.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import game.catalog.controller.model.GameCatalogData;
import game.catalog.controller.model.GameCatalogData.GameCatalogCharacterConcept;
import game.catalog.controller.model.GameCatalogData.GameCatalogMechanic;
import game.catalog.controller.model.GameCatalogData.GameCatalogStoryElement;
import game.catalog.dao.CharacterConceptDao;
import game.catalog.dao.GameCatalogDao;
import game.catalog.dao.MechanicDao;
import game.catalog.dao.StoryElementDao;
import game.catalog.entity.CharacterConcept;
import game.catalog.entity.Game;
import game.catalog.entity.Mechanic;
import game.catalog.entity.StoryElement;

@Service
public class GameCatalogService {
	@Autowired
	private GameCatalogDao gameCatalogDao;	
	@Autowired
	private MechanicDao mechanicDao;
	@Autowired
	private StoryElementDao sElementDao;
	@Autowired
	private CharacterConceptDao characterConceptDao;
		
	// ----------- GAME METHODS --------- //
	
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
	public void deleteGameById(Long gameId) {
		Game game = findGameById(gameId);
		gameCatalogDao.delete(game);
	}
	
	// ----------- MECHANIC METHODS --------- //
	
	@Transactional(readOnly = false)
	public GameCatalogMechanic saveMechanic(Long gameId, GameCatalogMechanic mechanic) {
		Game game = findGameById(gameId);
		Long mechanicId = mechanic.getMechanicId();
		Mechanic mech = findOrCreateMechanic(gameId, mechanicId);
		
		copyMechanicFields(mech, mechanic);
		
		mech.getGames().add(game);
		
		Mechanic dbMechanic = mechanicDao.save(mech);
		
		return new GameCatalogMechanic(dbMechanic);
	}
	
	private void copyMechanicFields(Mechanic mech, GameCatalogMechanic mechanic) {
		mech.setMechanicDescription(mechanic.getMechanicDescription());
		mech.setMechanicId(mechanic.getMechanicId());
		mech.setMechanicName(mechanic.getMechanicName());	
	}

	private Mechanic findOrCreateMechanic(Long gameId, Long mechanicId) {
		if(Objects.isNull(mechanicId)) {
			return new Mechanic();
		}
		return findMechanicById(gameId, mechanicId);
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
	
	@Transactional(readOnly = false)
	public void deleteMechanicById(Long gameId, Long mechanicId) {
		Mechanic mechanic = findMechanicById(gameId, mechanicId);
		boolean found = false;
		for (Game game: mechanic.getGames()){
			if(game.getGameId() == gameId) {
				found = true;
				mechanicDao.deleteById(mechanicId);
				break;
			}
			if (!found) {
				throw new IllegalArgumentException("Mechanic with ID=" + mechanicId + "was not found.");
			}
		}
	}
	
	// ----------- STORY ELEMENT METHODS --------- //
	
	@Transactional(readOnly = false)
	public GameCatalogStoryElement saveStoryElement(Long gameId, GameCatalogStoryElement gameCatalogStoryElement) {
		Game game = findGameById(gameId);
		Long sElementId = gameCatalogStoryElement.getStoryElementId();
		StoryElement sElement = findOrCreateStoryElement(gameId, sElementId);
		
		copyStoryElementFields(sElement, gameCatalogStoryElement);
		
		sElement.getGames().add(game);
		
		StoryElement dbElement = sElementDao.save(sElement);
		
		return new GameCatalogStoryElement(dbElement);
	}
	
	private void copyStoryElementFields(StoryElement sElement, GameCatalogStoryElement gameCatalogStoryElement) {
		sElement.setStoryElementDescription(gameCatalogStoryElement.getStoryElementDescription());
		sElement.setStoryElementId(gameCatalogStoryElement.getStoryElementId());
		sElement.setStoryElementName(gameCatalogStoryElement.getStoryElementName());
	}

	private StoryElement findOrCreateStoryElement(Long gameId, Long storyElementId) {
		if(Objects.isNull(storyElementId)) {
			return new StoryElement();
		}
		return findStoryElementById(gameId, storyElementId);
	}

	public StoryElement findStoryElementById(Long gameId, Long storyElementId) {
		StoryElement sElement = sElementDao.findById(storyElementId)
				.orElseThrow(() -> new NoSuchElementException("Story element with ID=" + storyElementId + " was not found."));
		
		boolean found = false;
		
		for(Game game : sElement.getGames()) {
			if(game.getGameId() == gameId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("The mechanic with ID=" + storyElementId + " does not belong to game with ID=" + gameId);
		}
		
		return sElement;
	}
	
	
	// ----------- CHARACTER CONCEPT METHODS --------- //
	
	@Transactional(readOnly = false)
	public GameCatalogCharacterConcept saveCharacterConcept(Long gameId, GameCatalogCharacterConcept gameCatalogCharacterConcept) {
		Game game = findGameById(gameId);
		Long characterConceptId = gameCatalogCharacterConcept.getCharacterConceptId();
		CharacterConcept characterConcept = findOrCreateCharacterConcept(gameId, characterConceptId);
		
		copyCharacterConceptFields(characterConcept, gameCatalogCharacterConcept);
		
		characterConcept.setGame(game);
		game.getCharacterConcepts().add(characterConcept);
		
		CharacterConcept dbCharacterConcept = characterConceptDao.save(characterConcept);
		
		return new GameCatalogCharacterConcept(dbCharacterConcept);
	}

	private void copyCharacterConceptFields(CharacterConcept characterConcept,
			GameCatalogCharacterConcept gameCatalogCharacterConcept) {
		characterConcept.setCharacterConceptDescription(gameCatalogCharacterConcept.getCharacterConceptDescription());
		characterConcept.setCharacterConceptId(gameCatalogCharacterConcept.getCharacterConceptId());
		characterConcept.setCharacterConceptName(gameCatalogCharacterConcept.getCharacterConceptName());
	}

	private CharacterConcept findOrCreateCharacterConcept(Long gameId, Long characterConceptId) {
		if(Objects.isNull(characterConceptId)) {
			return new CharacterConcept();
		}
		
		return findCharacterConceptById(gameId, characterConceptId);
	}

	private CharacterConcept findCharacterConceptById(Long gameId, Long characterConceptId) {
		CharacterConcept characterConcept = characterConceptDao.findById(characterConceptId).orElseThrow(
					() -> new NoSuchElementException("Character concept with ID=" + characterConceptId + " was not found."));
		
		if (characterConcept.getGame().getGameId() != gameId) {
			throw new IllegalArgumentException("Character concept with ID=" + characterConceptId + " does not belong to game with ID=" + gameId + ".");
		}
		
		return characterConcept;
	}
}
