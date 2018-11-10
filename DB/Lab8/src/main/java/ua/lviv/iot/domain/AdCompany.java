package ua.lviv.iot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ad_company", schema = "football")
public class AdCompany {
    private String companyName;
    private Double budget;
    private String industry;
    private String counry;
    private List<FootballPlayer> players;


    @Id
    @Column(name = "company_name", nullable = false, length = 50)
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "budget", nullable = true, precision = 0)
    public Double getBudget() {
        return budget;
    }
    public void setBudget(Double budget) {
        this.budget = budget;
    }

    @Basic
    @Column(name = "industry", nullable = true, length = 50)
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Basic
    @Column(name = "counry", nullable = true, length = 50)
    public String getCounry() {
        return counry;
    }

    public void setCounry(String counry) {
        this.counry = counry;
    }


    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "playerscompanies", schema = "football",
            joinColumns = @JoinColumn(name = "company_name", referencedColumnName = "company_name", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "player_id", nullable = false))
    public List<FootballPlayer> getPlayers() {
        return players;
    }
    public void setPlayers(List<FootballPlayer> players) {
        this.players = players;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdCompany that = (AdCompany) o;

        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (budget != null ? !budget.equals(that.budget) : that.budget != null) return false;
        if (industry != null ? !industry.equals(that.industry) : that.industry != null) return false;
        if (counry != null ? !counry.equals(that.counry) : that.counry != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyName != null ? companyName.hashCode() : 0;
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
        result = 31 * result + (industry != null ? industry.hashCode() : 0);
        result = 31 * result + (counry != null ? counry.hashCode() : 0);
        return result;
    }
}