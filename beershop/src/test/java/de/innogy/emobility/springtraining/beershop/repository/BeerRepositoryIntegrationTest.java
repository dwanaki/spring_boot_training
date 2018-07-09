package de.innogy.emobility.springtraining.beershop.repository;

import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@Slf4j
public class BeerRepositoryIntegrationTest {

    @Autowired
    private BeerItemRepository beerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testRepositoryBeerItemContents() {
        List<BeerItem> beerItems = beerRepository.findAll();
        for (BeerItem beerItem : beerItems) {
            log.info("Beer Item: "+beerItem);
        }
        Assert.notEmpty(beerItems, "Empty Beer Item List!");
    }

    @Test
    public void simpleJdbcTest() {
        List<Map<String,Object>> beerItems = jdbcTemplate.queryForList("select * FROM BEER_ITEM");
        for (Map<String, Object> beerItem : beerItems) {
            log.info("Beer Item: "+beerItem);
        }
        Assert.notEmpty(beerItems, "Empty Beer Item List!");
    }
}
