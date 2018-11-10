package ua.lviv.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.domain.Logger;

@Repository
public interface LoggerRepository extends JpaRepository<Logger, Integer> {
}
