package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "initial-stock")
public class InitialStock {
    public List<BeerItem> beers = new ArrayList<>();
}
