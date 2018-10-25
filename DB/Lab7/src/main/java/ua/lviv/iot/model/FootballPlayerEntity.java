package ua.lviv.iot.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "football_player", schema = "lab6")
@IdClass(FootballPlayerEntityPK.class)
public class FootballPlayerEntity {
    private String lastName;
    private String firstName;
    private Byte yearsOfExperience;
    private Integer playerId;
    private List<AddCompanyEntity> companies;


    @Id
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Id
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "years_of_experience")
    public Byte getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Byte yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Basic
    @Column(name = "player_id")
    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }


    @ManyToMany(mappedBy = "players")
    public List<AddCompanyEntity> getCompanies() {
        return companies;
    }

    public void setCompanies(List<AddCompanyEntity> companies) {
        this.companies = companies;
    }


    private FootballClubEntity clubByClub;

    @ManyToOne
    @JoinColumn(name = "club_name", referencedColumnName = "club_name", nullable = false)
    public FootballClubEntity getClubByClub() {
        return clubByClub;
    }

    public void setClubByClub(FootballClubEntity clubByClub) {
        this.clubByClub = clubByClub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FootballPlayerEntity that = (FootballPlayerEntity) o;

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

    public void addAddCompanyEntity(AddCompanyEntity addCompanyEntity){
        if(!getCompanies().contains(addCompanyEntity)){
            getCompanies().add(addCompanyEntity);
        }
        if(!addCompanyEntity.getPlayers().contains(this)){
            addCompanyEntity.getPlayers().add(this);
        }
    }
}
