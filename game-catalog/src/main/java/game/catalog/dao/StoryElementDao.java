package game.catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import game.catalog.entity.StoryElement;

public interface StoryElementDao extends JpaRepository<StoryElement, Long> {

}
