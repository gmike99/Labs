package ua.lviv.iot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.domain.FootballClub;
import ua.lviv.iot.exceptions.NoSuchClubException;
import ua.lviv.iot.repository.ClubRepository;
import ua.lviv.iot.repository.PlayerRepository;

import java.util.List;

@Service
public class ClubService {
    @Autowired
    ClubRepository clubRepository;

    @Autowired
    PlayerRepository playerRepository;

    public List<FootballClub> getAllClubs() {return clubRepository.findAll();}

    public FootballClub getClub(String clubName) throws NoSuchClubException {
        FootballClub club = clubRepository.findById(clubName).get();
        if (club == null) throw new NoSuchClubException();
        return club;
    }

}
