package de.innogy.emobility.springtraining.beershop.controller;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    List<BeerItem> beerItems;
}
