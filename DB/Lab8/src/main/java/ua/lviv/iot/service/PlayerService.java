package ua.lviv.iot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.domain.AdCompany;
import ua.lviv.iot.domain.FootballClub;
import ua.lviv.iot.domain.FootballPlayer;
import ua.lviv.iot.domain.Logger;
import ua.lviv.iot.exceptions.ExistsCompanyForPlayerException;
import ua.lviv.iot.exceptions.NoSuchClubException;
import ua.lviv.iot.exceptions.NoSuchCompanyException;
import ua.lviv.iot.exceptions.NoSuchPlayerException;
import ua.lviv.iot.repository.ClubRepository;
import ua.lviv.iot.repository.CompanyRepository;
import ua.lviv.iot.repository.LoggerRepository;
import ua.lviv.iot.repository.PlayerRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    LoggerRepository loggerRepository;

    public List<FootballPlayer> getPlayersByCompanyName(String companyName) throws NoSuchCompanyException {
        AdCompany company = companyRepository.findById(companyName).get();
        if (company == null) throw new NoSuchCompanyException();
        return company.getPlayers();
    }

    public FootballPlayer getPlayer(Integer playerId) throws NoSuchPlayerException {
        FootballPlayer player = playerRepository.findById(playerId).get();
        if (player == null) throw new NoSuchPlayerException();
        return player;
    }

    public List<FootballPlayer> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<FootballPlayer> getPlayersByClubName(String clubName) throws NoSuchClubException {
        FootballClub club = clubRepository.findById(clubName).get();
        if (club == null) throw new NoSuchClubException();
        return club.getPlayersByClub();
    }

    @Transactional
    public void createPlayer(FootballPlayer player, String clubName) throws NoSuchClubException {
        if (!clubName.equals("")) {
            FootballClub club = clubRepository.findById(clubName).get();
            if (club == null) throw new NoSuchClubException();
            player.setClubByPlayer(club);
        }
        playerRepository.save(player);
    }


    @Transactional
    public void updatePlayer(FootballPlayer newPlayer, Integer oldPlayerId, String clubName)
            throws NoSuchPlayerException, NoSuchClubException {
        FootballClub club = clubRepository.findById(clubName).get();
        if (!clubName.equals("")) {
            if (club == null) throw new NoSuchClubException();
        }

        FootballPlayer player = playerRepository.findById(oldPlayerId).get();
        if (player == null) throw new NoSuchPlayerException();

        player.setLastName(newPlayer.getLastName());
        player.setFirstName(newPlayer.getFirstName());
        if (!clubName.equals("")) player.setClubByPlayer(club);
        else player.setClubByPlayer(null);
        player.setYearsOfExperience(newPlayer.getYearsOfExperience());
        playerRepository.save(player);
    }


    @Transactional
    public void deletePlayer(Integer playerId)
            throws NoSuchPlayerException, NoSuchClubException {
        FootballPlayer player = playerRepository.findById(playerId).get();
        if (player == null) throw new NoSuchPlayerException();
        playerRepository.delete(player);
    }

    @Transactional
    public void addCompanyForPlayer(Integer playerId, String companyName)
            throws NoSuchCompanyException, NoSuchPlayerException, ExistsCompanyForPlayerException {
        FootballPlayer footballPlayer;
        AdCompany adCompany;
        if (playerRepository.findById(playerId).isPresent()) {
            footballPlayer = playerRepository.findById(playerId).get();
        } else throw new NoSuchPlayerException();

        if (companyRepository.findById(companyName).isPresent()) {
            adCompany = companyRepository.findById(companyName).get();
        } else throw new NoSuchCompanyException();


        Logger logger = new Logger();
        logger.setCompanyName(adCompany.getCompanyName());
        logger.setPlayerId(footballPlayer.getPlayerId());

        if (footballPlayer.getCompanies().contains(adCompany)) throw new ExistsCompanyForPlayerException();
        loggerRepository.save(logger);
    }

}
