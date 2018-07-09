package de.innogy.emobility.springtraining.beershop.repository;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class BeerRepositoryMockTest {

    @Mock
    private BeerItemRepository beerItemRepository;

    @Before
    public void setupMocks() {
        List<BeerItem> beerItems = new ArrayList<>();
        beerItems.add(new BeerItem("Innogy Premium Pils",500,4.8,100));
        beerItems.add(new BeerItem("Innogy Energy Stout",500,8.6,100));
        Mockito.when(beerItemRepository.findAll()).thenReturn(beerItems);
    }

    @Test
    public void testRepositoryBeerItemContents() {
        List<BeerItem> beerItems = beerItemRepository.findAll();
        for (BeerItem beerItem : beerItems) {
            log.info("Beer Item: "+beerItem);
        }
        Assert.notEmpty(beerItems, "Empty Beer Item List!");
    }
}
