package ua.lviv.iot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "football_player", schema = "football")
public class FootballPlayer {
    private String lastName;
    private String firstName;
    private Byte yearsOfExperience;
    private FootballClub clubByPlayer;
    private Integer playerId;


    @Basic
    @Column(name = "last_name", nullable = false, length = 30)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 30)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "years_of_experience", nullable = true)
    public Byte getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Byte yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @JsonIgnore
    @Id
    @Column(name = "player_id", nullable = false)
    @GenericGenerator(name="auto" , strategy="increment")
    @GeneratedValue(generator = "auto")
    public Integer getPlayerId() {
        return playerId;
    }
    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    private List<AdCompany> companies;

    @ManyToMany(mappedBy = "players")
    public List<AdCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(List<AdCompany> companies) {
        this.companies = companies;
    }


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "club_name", referencedColumnName = "club_name", nullable = false)
    public FootballClub getClubByPlayer() {
        return clubByPlayer;
    }

    public void setClubByPlayer(FootballClub clubByPlayer) {
        this.clubByPlayer = clubByPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FootballPlayer that = (FootballPlayer) o;

        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (yearsOfExperience != null ? !yearsOfExperience.equals(that.yearsOfExperience) : that.yearsOfExperience != null)
            return false;
        if (playerId != null ? !playerId.equals(that.playerId) : that.playerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (yearsOfExperience != null ? yearsOfExperience.hashCode() : 0);
        result = 31 * result + (playerId != null ? playerId.hashCode() : 0);
        return result;
    }
}