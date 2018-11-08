package de.innogy.emobility.springtraining.beershop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
//TODO: make this class your entity!
public class BeerItem {

    @NotNull
    private String name;

    @NotNull
    private Double alcoholVol;

    @NotNull
    @JsonIgnore
    private Integer stock;

}
