package co.uk.kenyoncomputing.mixfix.controllers;

import co.uk.kenyoncomputing.mixfix.models.Search;
import co.uk.kenyoncomputing.mixfix.models.TestPOJO;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@RestController
@RequestMapping(value = "search")
public class SearchController {

    private static Logger logger = LoggerFactory.getLogger(SearchController.class.getName());

    @PostMapping(value = "/api")
    public StringBuffer searchQ(@RequestBody Search search) throws IOException {

        String searchQuery = search.getQuery().equals("") ? "%2B" : search.getQuery().replaceAll("\\s+", "%2B");

        String url = "https://api.spotify.com/v1/search?q=" + searchQuery + "&type=track&limit=" + search.getLimit() + "&offset=" + search.getOffset();

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        request.addHeader("User-Agent", USER_AGENT);
        //    @Value("${client-id}")
        String token = "BQD7DHI8XjdqL1Y7DmciVPjD6XUQbxhl4L3d-KJGGGfjGPanqyjtkyH3-mulcS1_MRM87v2fkhY4jCxOE47Tg1ohyrqYku4Il99dqRtBB8XfWfAsn9MnrO09XdiyhXHV_y1eqVTbbUgITVY6cyKZ0PWe";
        request.addHeader("Authorization", "Bearer " + token);
        request.addHeader("Accept", "application/json");
        HttpResponse response = client.execute(request);

        logger.info("Response Code : " + response.getStatusLine().getStatusCode());

        if (response.getStatusLine().getStatusCode() == 401) {
            String error = "The server did not respond properly. Please check spotify authorization token from application.yml or get new token from (https://developer.spotify.com/web-api/console/get-search-item/).";
            logger.error(error);
            throw new RuntimeException(error);
        } else {
            if (response.getStatusLine().getStatusCode() != 200)
                throw new RuntimeException("Something went wrong.");
        }

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        logger.info("Reading response...");
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        logger.info("Reading response completed successfully.");

        return result;
    }

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
