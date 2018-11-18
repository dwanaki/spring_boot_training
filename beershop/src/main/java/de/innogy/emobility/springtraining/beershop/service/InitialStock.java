package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@ConfigurationProperties(prefix = "initial-stock")
public class InitialStock {
    public List<BeerItem> beers= new ArrayList<>();
    
    
}
