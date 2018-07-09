package de.innogy.emobility.springtraining.beersupplier.repository;

import de.innogy.emobility.springtraining.beersupplier.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer,String> {



}
