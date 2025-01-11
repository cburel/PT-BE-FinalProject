package game.catalog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import game.catalog.controller.model.GameCatalogData;
import game.catalog.controller.model.GameCatalogData.GameCatalogMechanic;
import game.catalog.entity.Game;
import game.catalog.service.GameCatalogService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/game_catalog")
@Slf4j
public class GameCatalogController {
	//@Autowired
	private GameCatalogService gameCatalogService;
	
	// avoids autowiring and therefore testing doesn't require mocking
	public GameCatalogController(GameCatalogService service) {
		this.gameCatalogService = service;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public GameCatalogData insertGame(@RequestBody GameCatalogData gameCatalogData) {
		log.info("Creating game {}", gameCatalogData);
		return gameCatalogService.saveGame(gameCatalogData);
	}
	
	@PutMapping("/{gameId}")
	public GameCatalogData updateGame(@PathVariable Long gameId, @RequestBody GameCatalogData gameCatalogData) {
		gameCatalogData.setGameId(gameId);
		log.info("Updating game {}", gameCatalogData);
		return gameCatalogService.saveGame(gameCatalogData);
	}
	
	@GetMapping
	public List<Game> getAllGames(){
		log.info("Retrieving all games.");
		List<Game> games = gameCatalogService.getAllGames();
		return games;
	}
	
	@GetMapping("/{gameId}")
	public GameCatalogData getGameById(@PathVariable Long gameId) {
		log.info("Getting game with ID={}", gameId);
		GameCatalogData game = gameCatalogService.getGameById(gameId);
		return game;
	}
	
	@PostMapping("/game_catalog/{mechanicId}/mechanic")
	@ResponseStatus(code = HttpStatus.CREATED)
	public GameCatalogMechanic addMechanicToGame(@PathVariable Long gameId, @RequestBody GameCatalogMechanic mechanic) {
		log.info("Adding mechanic {} to game with ID={}", mechanic, gameId);
		return gameCatalogService.saveMechanic(gameId, mechanic);
	}
}
