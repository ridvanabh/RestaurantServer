package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "countries")
public class Country extends Model {

    @Id
    public Long id;

    @Column(nullable = false, unique = true)
    @Size(max = 50)
    private String country;

    public Country(String country) {
        this.country = country;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static Finder<Long, Country> getFinder() {
        return finder;
    }

    public static final Finder<Long, Country> finder = new Finder<>(Country.class);



}
