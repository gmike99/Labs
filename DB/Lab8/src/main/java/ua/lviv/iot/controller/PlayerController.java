package ua.lviv.iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.DTO.FootballClubDTO;
import ua.lviv.iot.DTO.FootballPlayerDTO;
import ua.lviv.iot.domain.FootballPlayer;
import ua.lviv.iot.exceptions.ExistsCompanyForPlayerException;
import ua.lviv.iot.exceptions.NoSuchClubException;
import ua.lviv.iot.exceptions.NoSuchCompanyException;
import ua.lviv.iot.exceptions.NoSuchPlayerException;
import ua.lviv.iot.repository.PlayerRepository;
import ua.lviv.iot.service.PlayerService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerRepository repository;

    @GetMapping(value = "/api/player/company/{companyName}")
    public ResponseEntity<List<FootballPlayerDTO>> getPlayersByCompanyName(@PathVariable String companyName)
            throws NoSuchPlayerException, NoSuchCompanyException, NoSuchClubException {
        List<FootballPlayer> playerList = playerService.getPlayersByCompanyName(companyName);

        Link link = linkTo(methodOn(PlayerController.class).getAllPlayers()).withSelfRel();

        List<FootballPlayerDTO> playersDTO = new ArrayList<>();
        for (FootballPlayer entity : playerList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getPlayerId()).withSelfRel();
            FootballPlayerDTO dto = new FootballPlayerDTO(entity, selfLink);
            playersDTO.add(dto);
        }

        return new ResponseEntity<>(playersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/player/{playerId}")
    public @ResponseBody ResponseEntity<FootballPlayerDTO> getPlayer(@PathVariable Integer playerId)
            throws NoSuchPlayerException, NoSuchCompanyException, NoSuchClubException {
        FootballPlayer player = playerService.getPlayer(playerId);
        Link link = linkTo(methodOn(PlayerController.class).getPlayer(playerId)).withSelfRel();

        FootballPlayerDTO playerDTO = new FootballPlayerDTO(player, link);

        return new ResponseEntity<>(playerDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/player")
    public ResponseEntity<List<FootballPlayerDTO>> getAllPlayers()
            throws NoSuchPlayerException, NoSuchCompanyException, NoSuchClubException {
        List<FootballPlayer> playerList = playerService.getAllPlayers();
        Link link = linkTo(methodOn(PlayerController.class).getAllPlayers()).withSelfRel();

        List<FootballPlayerDTO> personsDTO = new ArrayList<>();
        for (FootballPlayer entity : playerList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getPlayerId()).withSelfRel();
            FootballPlayerDTO dto = new FootballPlayerDTO(entity, selfLink);
            personsDTO.add(dto);
        }

        return new ResponseEntity<>(personsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/player/club/{clubName}")
    public ResponseEntity<List<FootballPlayerDTO>> getPlayersByClubName(@PathVariable String clubName)
            throws NoSuchClubException, NoSuchPlayerException, NoSuchCompanyException {
        List<FootballPlayer> playerList = playerService.getPlayersByClubName(clubName);
        Link link = linkTo(methodOn(PlayerController.class).getAllPlayers()).withSelfRel();

        List<FootballPlayerDTO> personsDTO = new ArrayList<>();
        for (FootballPlayer entity : playerList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getPlayerId()).withSelfRel();
            FootballPlayerDTO dto = new FootballPlayerDTO(entity, selfLink);
            personsDTO.add(dto);
        }

        return new ResponseEntity<>(personsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/player/club/{clubName}")
    public  ResponseEntity<String> addPlayer(@RequestBody FootballPlayer newPlayer, @PathVariable String clubName)
            throws NoSuchClubException, NoSuchPlayerException, NoSuchCompanyException {

        playerService.createPlayer(newPlayer, clubName);

        return new ResponseEntity<>("Football Player Created", HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/player/{oldPlayerId}/club/{clubName}")
    public  ResponseEntity<String> updatePerson(@RequestBody FootballPlayer newPlayer,
                                                   @PathVariable Integer oldPlayerId,
                                                @PathVariable String clubName)
            throws NoSuchClubException, NoSuchPlayerException, NoSuchCompanyException {
        playerService.updatePlayer(newPlayer, oldPlayerId, clubName);


        return new ResponseEntity<>("Player Updated", HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/player/{playerId}")
    public ResponseEntity deletePerson(@PathVariable Integer playerId)
            throws NoSuchClubException, NoSuchPlayerException {
        playerService.deletePlayer(playerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "api/player/{playerId}/company/{companyName}")
    public ResponseEntity addCompanyForPlayer(@PathVariable Integer playerId,
                                              @PathVariable String companyName)
            throws NoSuchPlayerException, NoSuchCompanyException, ExistsCompanyForPlayerException {
        playerService.addCompanyForPlayer(playerId, companyName);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
