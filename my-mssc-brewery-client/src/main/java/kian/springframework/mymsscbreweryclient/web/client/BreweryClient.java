package kian.springframework.mymsscbreweryclient.web.client;

import kian.springframework.mymsscbreweryclient.web.model.BeerDto;
import kian.springframework.mymsscbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

// Component is the parent Stereotype, which let's Spring create a bean per class.
// It's class leveled bean vs @bean which is method leveled bean!
// Which has a specific operation to do and its lifecycle is handled by Spring
@Component
// Add metadata, so we can use this component's bean in application.properties file and configure it
// In this case we want the partial api URIs to be hardcoded and only let the host part of the URI to be
// Configured in the properties file.
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = true)
public class BreweryClient {

    public final String BEER_PATH_V1 = "/api/v1/beer/";
    private final String CUSTOMER_PATH_V1 = "/api/v1/customer/";

    // Api hosts changes on different deployments!
    // So we set this Spring in the properties file.
    private String apihost;

    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID uuid) {
        return restTemplate.getForObject(apihost + BEER_PATH_V1 + uuid.toString(), BeerDto.class);
    }

    public URI saveNewBeer(BeerDto beerDto) {
        return restTemplate.postForLocation(apihost + BEER_PATH_V1, beerDto);
    }

    public void updateBeer(UUID uuid, BeerDto beerDto) {
        restTemplate.put(apihost + BEER_PATH_V1 + UUID.randomUUID().toString(), beerDto);
    }

    public void deleteBeer(UUID uuid) {
        restTemplate.delete(apihost + BEER_PATH_V1 + UUID.randomUUID());
    }

    public CustomerDto getCustomerById(UUID uuid) {
        return restTemplate.getForObject(apihost + CUSTOMER_PATH_V1 + UUID.randomUUID().toString(), CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto) {
        return restTemplate.postForLocation(apihost + CUSTOMER_PATH_V1, customerDto);
    }

    public void updateCustomer(CustomerDto customerDto) {
        restTemplate.put(apihost + CUSTOMER_PATH_V1 + customerDto.getId(), customerDto);
    }

    public void deleteCustomer(CustomerDto customerDto) {
        restTemplate.delete(apihost + CUSTOMER_PATH_V1 + customerDto.getId());
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }
}
