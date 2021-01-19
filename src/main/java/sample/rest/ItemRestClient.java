package sample.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.ItemDto;
import sample.dto.ItemSaveDto;
import sample.handler.ProcessFinishedHandler;

import java.util.Arrays;
import java.util.List;

public class ItemRestClient {

    private static final String ITEMS_URL = "http://localhost:8080/items";

    private final RestTemplate restTemplate;

    public ItemRestClient() {
        restTemplate = new RestTemplate();
    }

    public List<ItemDto> getItems() {
        ResponseEntity<ItemDto[]> responseEntity = restTemplate.getForEntity(ITEMS_URL, ItemDto[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public void saveItem(ItemSaveDto itemSaveDto, ProcessFinishedHandler processFinishedHandler) {
        ResponseEntity<ItemDto> itemDtoResponseEntity = restTemplate.postForEntity(ITEMS_URL, itemSaveDto, ItemDto.class);
        if (HttpStatus.OK.equals(itemDtoResponseEntity.getStatusCode())) {
            processFinishedHandler.handle();
        } else {
            throw new RuntimeException("Can't save item: " + itemSaveDto);
        }
    }
}
