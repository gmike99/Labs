package ua.lviv.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.domain.AdCompany;

@Repository
public interface CompanyRepository extends JpaRepository<AdCompany, String> {
}
