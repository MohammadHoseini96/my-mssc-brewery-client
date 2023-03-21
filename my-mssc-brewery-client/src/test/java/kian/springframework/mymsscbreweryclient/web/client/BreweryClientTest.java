package kian.springframework.mymsscbreweryclient.web.client;

import kian.springframework.mymsscbreweryclient.web.model.BeerDto;
import kian.springframework.mymsscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    @Test
    void getBeerById() {
        BeerDto dto = client.getBeerById(UUID.randomUUID());
        assertNotNull(dto);
    }

    @Test
    void saveNewBeer() {
        //given
        BeerDto dto = BeerDto.builder().beerName("Vodka").build();

        URI uri = client.saveNewBeer(dto);
        assertNotNull(uri);

        System.out.println(uri);
    }

    @Test
    void updateBeer() {
        //given
        BeerDto dto = BeerDto
                .builder()
                .beerName("Vodka")
                .id(UUID.randomUUID())
                .build();

        client.updateBeer(dto.getId(), dto);
    }

    @Test
    void deleteBeer() {
        client.deleteBeer(UUID.randomUUID());
    }

    @Test
    void getCustomerById() {
        CustomerDto customerDto = client.getCustomerById(UUID.randomUUID());
        assertNotNull(customerDto);
    }

    @Test
    void saveNewCustomer() {
        URI uri = client.saveNewCustomer(CustomerDto.builder().id(UUID.randomUUID()).name("Kian").build());
        assertNotNull(uri);

        System.out.println(uri);
    }

    @Test
    void updateCustomer() {
        client.updateCustomer(CustomerDto.builder().id(UUID.randomUUID()).name("Kian").build());
    }

    @Test
    void deleteCustomer() {
        client.deleteCustomer(CustomerDto.builder().id(UUID.randomUUID()).name("Kian").build());
    }

}