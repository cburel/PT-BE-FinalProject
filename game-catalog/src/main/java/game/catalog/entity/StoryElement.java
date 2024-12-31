package game.catalog.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class StoryElement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long storyElementId;
	
	String storyElementName;
	String storyElementDescription;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "storyElements", cascade = CascadeType.PERSIST)
	Set<Game> games;
}
