package org.gs;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Movie extends PanacheEntity {

    @Column(length = 100)
    public String title;

    @Column(length = 200)
    public String description;

    public String director;

    public String country;
}
