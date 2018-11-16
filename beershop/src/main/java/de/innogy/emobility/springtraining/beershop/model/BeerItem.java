package de.innogy.emobility.springtraining.beershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BEER_ITEM")
public class BeerItem {

    @NotNull
    @Column(name="BEER_NAME")
    @Id
    private String name;

    @NotNull
    @Column(name="ALCOHOL_VOL")
    private Double alcoholVol;

    @NotNull
    @Column(name="STOCK")
    private Integer stock;

}
