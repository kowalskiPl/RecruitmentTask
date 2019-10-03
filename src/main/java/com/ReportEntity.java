package com;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Report", uniqueConstraints = {@UniqueConstraint(columnNames = "report_id")})
public class ReportEntity implements Serializable {
    private static final long serialVersionUID = -6993154676L;

    @Id
    @Column(name = "report_id", unique = true, nullable = false)
    private Integer reportId;
    @Column(name = "query_criteria_character_phrase", nullable = false, length = 100)
    private String queryCriteriaCharacterPhrase;
    @Column(name = "query_criteria_planet_name", nullable = false, length = 100)
    private String queryCriteriaPlanetName;
    @Column(name = "film_id", unique = true)
    private Integer filmId;
    @Column(name = "film_name", length = 100)
    private String filmName;
    @Column(name = "character_id", unique = true)
    private Integer characterId;
    @Column(name = "character_name", length = 100)
    private String characterName;
    @Column(name = "planet_id", unique = true)
    private Integer planetId;
    @Column(name = "planetName", length = 100)
    private String planetName;


    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getQueryCriteriaCharacterPhrase() {
        return queryCriteriaCharacterPhrase;
    }

    public void setQueryCriteriaCharacterPhrase(String queryCriteriaCharacterPhrase) {
        this.queryCriteriaCharacterPhrase = queryCriteriaCharacterPhrase;
    }

    public String getQueryCriteriaPlanetName() {
        return queryCriteriaPlanetName;
    }

    public void setQueryCriteriaPlanetName(String queryCriteriaPlanetName) {
        this.queryCriteriaPlanetName = queryCriteriaPlanetName;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Integer getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Integer planetId) {
        this.planetId = planetId;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    @Override
    public String toString() {
        return "ReportEntity{" +
                "reportId=" + reportId +
                ", queryCriteriaCharacterPhrase='" + queryCriteriaCharacterPhrase + '\'' +
                ", queryCriteriaPlanetName='" + queryCriteriaPlanetName + '\'' +
                ", filmId=" + filmId +
                ", filmName='" + filmName + '\'' +
                ", characterId=" + characterId +
                ", characterName='" + characterName + '\'' +
                ", planetId=" + planetId +
                ", planetName='" + planetName + '\'' +
                '}';
    }
}
