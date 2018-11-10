package ua.lviv.iot.DTO;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import ua.lviv.iot.controller.PlayerController;
import ua.lviv.iot.domain.AdCompany;
import ua.lviv.iot.domain.FootballPlayer;
import ua.lviv.iot.exceptions.NoSuchClubException;
import ua.lviv.iot.exceptions.NoSuchCompanyException;
import ua.lviv.iot.exceptions.NoSuchPlayerException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CompanyDTO extends ResourceSupport {

    AdCompany adCompany;

    public CompanyDTO(AdCompany company, Link selflink)
            throws NoSuchCompanyException, NoSuchPlayerException, NoSuchClubException{
        this.adCompany = company;
        add(selflink);
        add(linkTo(methodOn(PlayerController.class).getPlayersByCompanyName(company.getCompanyName())).withRel("players"));
    }

    public String getAddCompanyName() {
        return adCompany.getCompanyName();
    }

    public String getAddCompanyCounry() {
        return adCompany.getCounry();
    }

    public String getAddCompanyIndustry() {
        return adCompany.getIndustry();
    }

    public Double getAddCompanyBudget() {
        return adCompany.getBudget();
    }

    public List<Integer> getPlayers() {
        List<Integer> playerList = new ArrayList<>();
        for (FootballPlayer player: adCompany.getPlayers()) {
            playerList.add(player.getPlayerId());
        }

        return playerList;
    }
}
