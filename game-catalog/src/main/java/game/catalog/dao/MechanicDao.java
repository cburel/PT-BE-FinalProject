package game.catalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import game.catalog.entity.Mechanic;

public interface MechanicDao extends JpaRepository<Mechanic, Long> {

}
