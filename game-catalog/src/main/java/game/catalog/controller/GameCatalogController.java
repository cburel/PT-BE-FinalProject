package game.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import game.catalog.controller.model.GameCatalogData;
import game.catalog.service.GameCatalogService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/game_catalog")
@Slf4j
public class GameCatalogController {
	@Autowired
	private GameCatalogService gameCatalogService;
	
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
}
