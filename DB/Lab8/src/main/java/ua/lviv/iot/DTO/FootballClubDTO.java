package ua.lviv.iot.DTO;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import ua.lviv.iot.controller.PlayerController;
import ua.lviv.iot.domain.FootballClub;
import ua.lviv.iot.domain.FootballPlayer;
import ua.lviv.iot.exceptions.NoSuchClubException;
import ua.lviv.iot.exceptions.NoSuchCompanyException;
import ua.lviv.iot.exceptions.NoSuchPlayerException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class FootballClubDTO extends ResourceSupport {
    FootballClub footballClub;

    public FootballClubDTO(FootballClub club, Link selflink)
            throws NoSuchClubException, NoSuchPlayerException, NoSuchCompanyException {
        this.footballClub = club;
        add(selflink);
        add(linkTo(methodOn(PlayerController.class).getPlayersByClubName(club.getClubName())).withRel("players"));
    }

    public String getFootballClubName() {
        return footballClub.getClubName();
    }

    public String getFootballClubOwnerName() {
        return footballClub.getClubOwnerName();
    }

    public String getFootballClubCoachName() {
        return footballClub.getCoachName();
    }

    public String getFootballClubCountry() {
        return footballClub.getCountry();
    }

    public List<Integer> getPlayersByClub() {
        List<Integer> playerList = new ArrayList<>();
        for (FootballPlayer player: footballClub.getPlayersByClub()) {
            playerList.add(player.getPlayerId());
        }
        return playerList;
    }
}
