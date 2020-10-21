package co.uk.kenyoncomputing.mixfix.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("auth")
public class AuthController {

    @GetMapping("spotifyLogin")
    public ModelAndView spotifyLogin(ModelMap modelMap) {
        String state = RandomStringUtils.randomAlphabetic(16);

        modelMap.addAttribute("response_type", "code");
        modelMap.addAttribute("client_id", "9f8e38fa4a4b4121a8108c36674f4a67");
        modelMap.addAttribute("scope", "user-read-private user-read-email");
        modelMap.addAttribute("redirect_uri", "http://localhost:8080/callback");
        modelMap.addAttribute("state", RandomStringUtils.randomAlphabetic(16));
        return new ModelAndView("redirect:https://accounts.spotify.com/authorize?", modelMap);
    }

    @GetMapping("obtainToken")
    public ResponseEntity<String> obtainToken(String clientId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://accounts.spotify.com/authorize?client_id=" + clientId + "&response_type=token&redirect_uri=http://localhost:8080/callback";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(responseEntity);
        return responseEntity;
    }
}
