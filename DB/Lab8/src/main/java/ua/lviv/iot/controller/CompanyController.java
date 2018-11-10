package ua.lviv.iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.iot.DTO.CompanyDTO;
import ua.lviv.iot.domain.AdCompany;
import ua.lviv.iot.exceptions.NoSuchClubException;
import ua.lviv.iot.exceptions.NoSuchCompanyException;
import ua.lviv.iot.exceptions.NoSuchPlayerException;
import ua.lviv.iot.service.CompanyService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping(value = "/api/company/player/{playerId}")
    public ResponseEntity<List<CompanyDTO>> getCompaniesByPlayerId(@PathVariable Integer playerId)
            throws NoSuchPlayerException, NoSuchCompanyException, NoSuchClubException {
        List<AdCompany> companyList = companyService.getCompaniesByPlayerId(playerId);
        Link link = linkTo(methodOn(CompanyController.class).getAllCompanies()).withSelfRel();

        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (AdCompany entity : companyList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getCompanyName()).withSelfRel();
            CompanyDTO dto = new CompanyDTO(entity, selfLink);
            companiesDTO.add(dto);
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/company/{companyName}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable String companyName)
            throws NoSuchPlayerException, NoSuchCompanyException, NoSuchClubException {
        AdCompany company = companyService.getCompany(companyName);
        Link link = linkTo(methodOn(CompanyController.class).getCompany(companyName)).withSelfRel();

        CompanyDTO companyDTO = new CompanyDTO(company, link);

        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/company")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies()
            throws NoSuchPlayerException, NoSuchCompanyException, NoSuchClubException{
        List<AdCompany> bookList = companyService.getAllCompanies();
        Link link = linkTo(methodOn(CompanyController.class).getAllCompanies()).withSelfRel();

        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (AdCompany entity : bookList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getCompanyName()).withSelfRel();
            CompanyDTO dto = new CompanyDTO(entity, selfLink);
            companiesDTO.add(dto);
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }
}
