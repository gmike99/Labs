package ua.lviv.iot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.domain.AdCompany;
import ua.lviv.iot.domain.FootballPlayer;
import ua.lviv.iot.exceptions.NoSuchCompanyException;
import ua.lviv.iot.exceptions.NoSuchPlayerException;
import ua.lviv.iot.repository.CompanyRepository;
import ua.lviv.iot.repository.PlayerRepository;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PlayerRepository playerRepository;

    public List<AdCompany> getCompaniesByPlayerId(Integer playerId) throws NoSuchPlayerException {
        FootballPlayer player = playerRepository.findById(playerId).get();
        if (player == null) throw new NoSuchPlayerException();
        return player.getCompanies();
    }

    public AdCompany getCompany(String companyName) throws NoSuchCompanyException {
        AdCompany company = companyRepository.findById(companyName).get();
        if (company == null) throw new NoSuchCompanyException();
        return company;
    }

    public List<AdCompany> getAllCompanies() {
        return companyRepository.findAll();
    }

}
