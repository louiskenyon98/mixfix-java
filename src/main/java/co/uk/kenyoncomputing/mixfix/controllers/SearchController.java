package co.uk.kenyoncomputing.mixfix.controllers;

import co.uk.kenyoncomputing.mixfix.models.TestPOJO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "search")
public class SearchController {

    @GetMapping(value = "testApi")
    public Object testApi() {
        return new TestPOJO(1, "Louis", "He is very cool");
    }

    @GetMapping(value = "callTestApi")
    public ResponseEntity<TestPOJO> callTestApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/search/testApi";

        return restTemplate.getForEntity(url, TestPOJO.class);
    }
}
