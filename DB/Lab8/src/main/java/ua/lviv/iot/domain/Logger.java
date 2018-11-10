package ua.lviv.iot.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="playerscompanies")
public class Logger {
    private Integer id;
    private String companyName;
    private Integer playerId;


    @Id
    @Column(name="rel_id", nullable = false)
    @GenericGenerator(name="auto" , strategy="increment")
    @GeneratedValue(generator = "auto")
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }


    @Column(name="company_name", nullable = false, length = 50)
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    @Column(name="player_id", nullable = false)
    public Integer getPlayerId() { return playerId; }
    public void setPlayerId(Integer playerId) { this.playerId = playerId; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logger logger = (Logger) o;
        return Objects.equals(id, logger.id) &&
                Objects.equals(companyName, logger.companyName) &&
                Objects.equals(playerId, logger.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, playerId);
    }
}
