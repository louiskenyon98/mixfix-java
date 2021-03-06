package co.uk.kenyoncomputing.mixfix.controllers;

import co.uk.kenyoncomputing.mixfix.entities.AuthorizationCall;
import co.uk.kenyoncomputing.mixfix.entities.SpotifyTokens;
import co.uk.kenyoncomputing.mixfix.enums.Spotify;
import co.uk.kenyoncomputing.mixfix.repositories.SpotifyTokensRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
public class AuthController {

    private final SpotifyTokensRepository spotifyTokensRepository;

    @Autowired
    public AuthController(SpotifyTokensRepository spotifyTokensRepository) {
        this.spotifyTokensRepository = spotifyTokensRepository;
    }

    @GetMapping("spotifyLogin")
    public AuthorizationCall spotifyLogin(ModelMap modelMap, HttpServletResponse response) {
        String state = RandomStringUtils.randomAlphabetic(16);
        String stateKey = "spotify_auth_state";

        Cookie stateCookie = new Cookie(stateKey, state);

        response.addCookie(stateCookie);
        modelMap.addAttribute("response_type", Spotify.RESPONSE_TYPE.value());
        modelMap.addAttribute("client_id", Spotify.CLIENT_ID.value());
        modelMap.addAttribute("scope", Spotify.SCOPE.value());
        modelMap.addAttribute("redirect_uri", "http://localhost:8080/callback");
        modelMap.addAttribute("state", state);

        return new AuthorizationCall(Spotify.RESPONSE_TYPE.value(), Spotify.CLIENT_ID.value(), Spotify.SCOPE.value(), "http://localhost:8080/callback", state);

//        return new ModelAndView("redirect:https://accounts.spotify.com/authorize?", modelMap);
    }

    //use HttpServletRequest request to inspect the incoming request
    @GetMapping("callback")
    public ModelAndView callback(@RequestParam("code") String authCode, @RequestParam("state") String state, @CookieValue(value = "spotify_auth_state", defaultValue = "") String storedState, ModelMap modelMap) {
        //if state cookie doesn't match returned state value, or state cookie is blank
        if (state == null || !state.equals(storedState) || storedState.equals("")) {
            modelMap.addAttribute("error", "state_mismatch");
            return new ModelAndView("redirect:jimbo", modelMap);
        } else {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

            map.add("grant_type", "authorization_code");
            map.add("code", authCode);
            map.add("redirect_uri", "http://localhost:8080/callback");
            map.add("client_id", "9f8e38fa4a4b4121a8108c36674f4a67");
            map.add("client_secret", "3d5314d4ad274be38c36aeb8bfacdc80");

            String url = "https://accounts.spotify.com/api/token";

            HttpEntity<MultiValueMap<String, String>> postRequest = new HttpEntity<>(map, headers);

            ResponseEntity<SpotifyTokens> response = restTemplate.exchange(url, HttpMethod.POST, postRequest, SpotifyTokens.class);

            spotifyTokensRepository.save(Objects.requireNonNull(response.getBody()));

            return new ModelAndView("redirect:authSuccess");
        }
    }

    @GetMapping("obtainToken")
    public ResponseEntity<String> obtainToken(String clientId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://accounts.spotify.com/authorize?client_id=" + clientId + "&response_type=token&redirect_uri=http://localhost:8080/callback";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(responseEntity);
        return responseEntity;
    }

    @GetMapping("authSuccess")
    public String authSuccess() {
        return "Success";
    }

    @GetMapping("jimbo")
    public String hashRouter() {
        return "hash route";
    }
}
