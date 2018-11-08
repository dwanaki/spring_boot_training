package de.innogy.emobility.springtraining.beersupplier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Entity
@Table(name="BEER")
public class Beer {

    @NotNull
    @Id
    @Column(name="BEER_NAME")
    private String name;

    @NotNull
    @Column(name="ALCOHOL_VOL")
    private Double alcoholVol;

}
