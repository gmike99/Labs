package ua.lviv.iot.model;

import javax.persistence.*;
import java.util.List;
import ua.lviv.iot.model.FootballPlayerEntity;

@Entity
@Table(name = "football_club", schema = "lab7")
public class FootballClubEntity {
    private String clubName;
    private String clubOwnerName;
    private String coachName;
    private String country;

    @Id
    @Column(name = "club_name", nullable = false, length = 50)
    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @Basic
    @Column(name = "club_owner_name", nullable = true, length = 50)
    public String getClubOwnerName() {
        return clubOwnerName;
    }

    public void setClubOwnerName(String clubOwnerName) {
        this.clubOwnerName = clubOwnerName;
    }

    @Basic
    @Column(name = "coach_name", nullable = true, length = 50)
    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    @Basic
    @Column(name = "country", nullable = true, length = 50)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private List<FootballPlayerEntity> playersByClub;

    @OneToMany(mappedBy = "clubByClub")
    public List<FootballPlayerEntity> getPlayersByClub() {
        return playersByClub;
    }

    public void setPlayersByClub(List<FootballPlayerEntity> playersByClub) {
        this.playersByClub = playersByClub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FootballClubEntity that = (FootballClubEntity) o;

        if (clubName != null ? !clubName.equals(that.clubName) : that.clubName != null) return false;
        if (clubOwnerName != null ? !clubOwnerName.equals(that.clubOwnerName) : that.clubOwnerName != null)
            return false;
        if (coachName != null ? !coachName.equals(that.coachName) : that.coachName != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clubName != null ? clubName.hashCode() : 0;
        result = 31 * result + (clubOwnerName != null ? clubOwnerName.hashCode() : 0);
        result = 31 * result + (coachName != null ? coachName.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
