package ua.lviv.iot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.iot.DTO.FootballClubDTO;
import ua.lviv.iot.domain.FootballClub;
import ua.lviv.iot.exceptions.NoSuchClubException;
import ua.lviv.iot.exceptions.NoSuchCompanyException;
import ua.lviv.iot.exceptions.NoSuchPlayerException;
import ua.lviv.iot.service.ClubService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class ClubController {

    @Autowired
    ClubService clubService;

    @GetMapping(value = "/api/club")
    public ResponseEntity<List<FootballClubDTO>> getAllClubs()
            throws NoSuchClubException, NoSuchPlayerException, NoSuchCompanyException {
        List<FootballClub> clubList = clubService.getAllClubs();
        Link link = linkTo(methodOn(ClubController.class).getAllClubs()).withSelfRel();

        List<FootballClubDTO> clubsDTO = new ArrayList<>();
        for (FootballClub entity : clubList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getClubName()).withSelfRel();
            FootballClubDTO dto = new FootballClubDTO(entity, selfLink);
            clubsDTO.add(dto);
        }

        return new ResponseEntity<>(clubsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/club/{clubName}")
    public ResponseEntity<FootballClubDTO> getClub(@PathVariable String clubName)
            throws NoSuchClubException, NoSuchPlayerException, NoSuchCompanyException {
        FootballClub club = clubService.getClub(clubName);
        Link link = linkTo(methodOn(ClubController.class).getClub(clubName)).withSelfRel();

        FootballClubDTO cityDTO = new FootballClubDTO(club, link);

        return new ResponseEntity<>(cityDTO, HttpStatus.OK);
    }
}
