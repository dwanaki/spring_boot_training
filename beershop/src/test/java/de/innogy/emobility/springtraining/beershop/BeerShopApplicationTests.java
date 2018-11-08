package de.innogy.emobility.springtraining.beershop;

import de.innogy.emobility.springtraining.beershop.service.SupplyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeerShopApplicationTests {

	@MockBean
	private SupplyService supplyService;

	@Test
	public void contextLoads() {
	}

}
