package de.innogy.emobility.springtraining.beershop.service;

import de.innogy.emobility.springtraining.beershop.controller.DeliveryDTO;
import de.innogy.emobility.springtraining.beershop.controller.OrderDTO;
import de.innogy.emobility.springtraining.beershop.exception.OutOfBeerException;
import de.innogy.emobility.springtraining.beershop.exception.SorryAlcoholicOnlyDudeException;
import de.innogy.emobility.springtraining.beershop.model.BeerItem;
import de.innogy.emobility.springtraining.beershop.repository.BeerItemRepository;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class SupplyService {

    private RestTemplate restTemplate;

    @Value("${beer-producer.order.url}")
    private String beerProducerOrderUrl;

    @Value("${beer-producer.supply.all.url}")
    private String beerProducerBeersUrl;

    @Value("${clientName}")
    private String clientName;

    private BeerItemRepository beerItemRepository;

    private final JdbcTemplate jdbcTemplate;

    private RabbitTemplate rabbitTemplate;

    private DirectExchange directExchange;

    @Autowired
    public SupplyService(RestTemplate restTemplate, BeerItemRepository beerItemRepository, JdbcTemplate jdbcTemplate,
                         RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.restTemplate = restTemplate;
        this.beerItemRepository = beerItemRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @PostConstruct
    public void init() {
        BeerItem[] beerItems = restTemplate.getForObject(beerProducerBeersUrl, BeerItem[].class);
        for (BeerItem beerItem : beerItems) {
            beerItem.setStock(100);
        }
        beerItemRepository.saveAll(Arrays.asList(beerItems));
    }

    public void sendOrderToSupplier(BeerItem beerItem) {
        storeOutgoingOrder(beerItem.getName(), 1000);
        rabbitTemplate.convertAndSend(directExchange.getName(), "#{directQueue.name}",
                                      new OrderDTO(clientName, 1000, beerItem.getName()));
    }

    public DeliveryDTO orderBeer(OrderDTO orderDTO) throws OutOfBeerException {
        BeerItem beerItem = beerItemRepository.findById(orderDTO.getBeerName()).orElse(null);
        if (beerItem != null && beerItem.getStock() >= orderDTO.getQuantity()) {
            beerItem.setStock(beerItem.getStock() - orderDTO.getQuantity());
            return new DeliveryDTO(orderDTO.getQuantity(), beerItem);
        } else {
            throw new OutOfBeerException(
                    "Not enough quantity of Beer " + orderDTO.getBeerName() + " only " + (beerItem != null ? beerItem
                            .getStock() : 0) + " left", beerItem);
        }
    }

    public List<BeerItem> provideMenu() {
        return beerItemRepository.findAll();
    }

    public List<BeerItem> provideNonAlcoholicBeer() throws SorryAlcoholicOnlyDudeException {
        List<BeerItem> beers = beerItemRepository.provideNonAlcoholicSortiment();
        if (beers.isEmpty()) {
            throw new SorryAlcoholicOnlyDudeException();
        }
        return beers;
    }

    public List<BeerItem> provideBeerDueForResupply() {
        return beerItemRepository.findAllByStockIsLessThanEqual(5);
    }

    private void storeOutgoingOrder(String beerName, int quantity) {
        jdbcTemplate.update("INSERT INTO BEER_STOCK_ORDER (BEER_NAME, QUANTITY) VALUES (?,?)", beerName, quantity);
    }
}
