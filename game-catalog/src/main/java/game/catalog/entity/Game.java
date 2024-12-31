package game.catalog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long gameId;
	
	String gameTitle;
	String gameDescription;
	String gameReleaseDate;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<CharacterConcept> characterConcepts = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "game_mechanic", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "mechanic_id"))
	Set<Mechanic> mechanics = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "game_story_element", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "story_element_id"))
	Set<StoryElement> storyElements = new HashSet<>();
}
