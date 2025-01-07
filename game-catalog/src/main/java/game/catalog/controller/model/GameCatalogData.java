package game.catalog.controller.model;

import java.util.HashSet;
import java.util.Set;

import game.catalog.entity.CharacterConcept;
import game.catalog.entity.Game;
import game.catalog.entity.Mechanic;
import game.catalog.entity.StoryElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameCatalogData {
	private Long gameId;
	private String gameTitle;
	private String gameDescription;
	private String gameReleaseDate;
	private Set<GameCatalogCharacterConcept> characterConcepts = new HashSet<>();
	private Set<GameCatalogMechanic> mechanics = new HashSet<>();
	private Set<GameCatalogStoryElement> storyElements = new HashSet<>();
	
	public GameCatalogData(Game game){
		gameTitle = game.getGameTitle();
		gameDescription = game.getGameDescription();
		gameReleaseDate = game.getGameReleaseDate();
		
		for (CharacterConcept concept : game.getCharacterConcepts()) {
			characterConcepts.add(new GameCatalogCharacterConcept(concept));
		}
		
		for (Mechanic mechanic : game.getMechanics()) {
			mechanics.add(new GameCatalogMechanic(mechanic));
		}
		
		for (StoryElement storyElement : game.getStoryElements()) {
			storyElements.add(new GameCatalogStoryElement(storyElement));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class GameCatalogStoryElement {
		private Long storyElementId;
		private String storyElementName;
		private String storyElementDescription;
		//private Set<GameCatalogStoryElement> games = new HashSet<>();

		GameCatalogStoryElement(StoryElement storyElement){
			storyElementId = storyElement.getStoryElementId();
			storyElementName = storyElement.getStoryElementName();
			storyElementDescription = storyElement.getStoryElementDescription();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class GameCatalogMechanic {
		private Long mechanicId;
		private String mechanicName;
		private String mechanicDescription;
		//private Set<Game> games = new HashSet<>();
		
		GameCatalogMechanic(Mechanic mechanic){
			mechanicId = mechanic.getMechanicId();
			mechanicName = mechanic.getMechanicName();
			mechanicDescription = mechanic.getMechanicDescription();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class GameCatalogCharacterConcept{
		private Long characterConceptId;
		private String characterConceptName;
		private String characterConceptDescription;
		//private Game game;
		
		GameCatalogCharacterConcept(CharacterConcept concept){
			characterConceptId = concept.getCharacterConceptId();
			characterConceptName = concept.getCharacterConceptName();
			characterConceptDescription = concept.getCharacterConceptDescription();
		}
	}
}
