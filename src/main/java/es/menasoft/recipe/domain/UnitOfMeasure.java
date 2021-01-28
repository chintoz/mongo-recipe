package es.menasoft.recipe.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String description;

}
