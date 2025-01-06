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
	Long gameId;
	String gameTitle;
	String gameDescription;
	String gameReleaseDate;
	Set<CharacterConcept> characterConcepts = new HashSet<>();
	Set<Mechanic> mechanics = new HashSet<>();
	Set<StoryElement> storyElements = new HashSet<>();
	
	public GameCatalogData(Game game){
		gameTitle = game.getGameTitle();
		gameDescription = game.getGameDescription();
		gameReleaseDate = game.getGameReleaseDate();
		
		for (CharacterConcept concept : game.getCharacterConcepts()) {
			characterConcepts.add(concept);
		}
		
		for (Mechanic mechanic : game.getMechanics()) {
			mechanics.add(mechanic);
		}
		
		for (StoryElement storyElement : game.getStoryElements()) {
			storyElements.add(storyElement);
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class GameCatalogStoryElement {
		Long storyElementId;
		String storyElementName;
		String storyElementDescription;
		Set<GameCatalogStoryElement> games;

		GameCatalogStoryElement(StoryElement storyElement){
			storyElementId = storyElement.getStoryElementId();
			storyElementName = storyElement.getStoryElementName();
			storyElementDescription = storyElement.getStoryElementDescription();
		}
	}
	
	public static class GameCatalogMechanic {
		Long mechanicId;
		String mechanicName;
		String mechanicDescription;
		Set<Game> games;
		
		GameCatalogMechanic(Mechanic mechanic){
			mechanicId = mechanic.getMechanicId();
			mechanicName = mechanic.getMechanicName();
			mechanicDescription = mechanic.getMechanicDescription();
		}
	}
	
	public static class GameCatalogCharacterConcept{
		Long characterConceptId;
		String characterConceptName;
		String characterConceptDescription;
		Game game;
		
		GameCatalogCharacterConcept(CharacterConcept concept){
			characterConceptId = concept.getCharacterConceptId();
			characterConceptName = concept.getCharacterConceptName();
			characterConceptDescription = concept.getCharacterConceptDescription();
		}
	}
}
