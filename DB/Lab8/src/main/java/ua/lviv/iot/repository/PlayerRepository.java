package ua.lviv.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lviv.iot.domain.FootballPlayer;

public interface PlayerRepository extends JpaRepository<FootballPlayer, Integer> {
}
