package ua.lviv.iot.DTO;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import ua.lviv.iot.controller.CompanyController;
import ua.lviv.iot.domain.AdCompany;
import ua.lviv.iot.domain.FootballPlayer;
import ua.lviv.iot.exceptions.NoSuchClubException;
import ua.lviv.iot.exceptions.NoSuchCompanyException;
import ua.lviv.iot.exceptions.NoSuchPlayerException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


public class FootballPlayerDTO extends ResourceSupport {
    FootballPlayer footballPlayer;

    public FootballPlayerDTO(FootballPlayer player, Link selflink)
            throws NoSuchPlayerException, NoSuchCompanyException, NoSuchClubException {
        this.footballPlayer = player;
        add(selflink);
        add(linkTo(methodOn(CompanyController.class).getCompaniesByPlayerId(player.getPlayerId())).withRel("companies"));
    }

    public Integer getFootballPlayerId() {
        return footballPlayer.getPlayerId();
    }

    public String getFootballPlayerLastName() {
        return footballPlayer.getLastName();
    }

    public String getFootballPlayerFirstName() {
        return footballPlayer.getFirstName();
    }

    public Byte getYearsOfExperience() {
        return footballPlayer.getYearsOfExperience();
    }

    public String getClubByClub() {
        if (footballPlayer.getClubByPlayer() == null) return "";
        return footballPlayer.getClubByPlayer().getClubName();
    }

    public List<String> getCompanies() {
        List<String> nameList = new ArrayList<>();
        for (AdCompany company: footballPlayer.getCompanies()) {
            nameList.add(company.getCompanyName());
        }
        return nameList;
    }

}
