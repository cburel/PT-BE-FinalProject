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
public class Mechanic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long mechanicId;
	
	String mechanicName;
	String mechanicDescription;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "mechanics", cascade = CascadeType.PERSIST)
	Set<Game> games;
	
}
