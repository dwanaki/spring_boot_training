package de.innogy.emobility.springtraining.beershop.repository;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BeerItemRepository extends JpaRepository<BeerItem, String> {

    @Query("SELECT bi FROM BeerItem bi WHERE bi.alcoholVol = 0.0 and stock > 0")
    public List<BeerItem> provideNonAlcoholicSortiment();

    public List<BeerItem> findAllByStockIsLessThanEqual(Integer quantity);

}
