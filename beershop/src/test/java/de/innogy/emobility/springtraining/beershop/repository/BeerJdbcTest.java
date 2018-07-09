package de.innogy.emobility.springtraining.beershop.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@JdbcTest
public class BeerJdbcTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void initialize() {

        jdbcTemplate.execute(
                "CREATE TABLE BEER_ITEM (BEER_NAME VARCHAR(255), BOTTLE_SIZE_ML INTEGER, " +
                        "ALCOHOL_VOL DECIMAL, STOCK INTEGER, PRIMARY KEY (BEER_NAME))");

        jdbcTemplate.update("INSERT INTO BEER_ITEM (BEER_NAME, BOTTLE_SIZE_ML, ALCOHOL_VOL, STOCK) VALUES (?,?,?,?)",
                "Innogy Premium Pils",500,4.8,100);
        jdbcTemplate.update("INSERT INTO BEER_ITEM (BEER_NAME, BOTTLE_SIZE_ML, ALCOHOL_VOL, STOCK) VALUES (?,?,?,?)",
                "Innogy Energy Stout",500,8.6,100);
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
