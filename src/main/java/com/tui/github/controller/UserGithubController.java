package com.tui.github.controller;

import com.tui.github.model.GithubRepo;
import com.tui.github.service.IUserGithubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/users/repos")
@Slf4j
public class UserGithubController {

    private final IUserGithubService userGithubService;

    public UserGithubController(IUserGithubService userGithubService) {
        this.userGithubService = userGithubService;
    }

    /**
     * We used produces in the method to test the all three scenarios from Swagger UI.
     * @param userName as String
     * @return Flux<GithubRepo> This method return the GithubRepo as Flux.
     */
    @GetMapping(value = "/v1/{userName}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Flux<GithubRepo>> getUserGithubRepos(
            @PathVariable(name="userName") String userName){
        log.info("Inside getUserGithubRepos method - userName: {}",
                userName);
        return new ResponseEntity<>(userGithubService.getUserGithubRepos(userName), HttpStatus.OK);
    }

}
