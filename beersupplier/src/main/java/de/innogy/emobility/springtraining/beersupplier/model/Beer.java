package de.innogy.emobility.springtraining.beersupplier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Beer {

    @NotNull
    private String name;

    @NotNull
    private Double alcoholVol;

}
