package de.innogy.emobility.springtraining.beershop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO: make this class your entity!
public class BeerItem {

    @NotNull
    private String name;

    @NotNull
    private Double alcoholVol;

    @NotNull
    private Integer stock;

}
