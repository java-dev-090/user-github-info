package com.tui.github.service;

import com.tui.github.model.Branch;
import com.tui.github.model.GithubRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserGithubService implements IUserGithubService{

    private final WebClient webClient;

    public UserGithubService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<GithubRepo> getUserGithubRepos(String userName) {
        return webClient.get()
                .uri("/users/{userName}/repos", userName)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(GithubRepo.class))
                .filter(e-> !"true".equals(e.getIsFork()))
                .log()
                .flatMap( github -> {
                    return webClient.get()
                            .uri("/repos/{userName}/{branchName}/branches", userName,
                                    github.getName())
                            .retrieve()
                            .bodyToFlux(Branch.class)
                            .log()
                            .collectList()
                            .flatMap(branches -> Mono.just(new GithubRepo(github.getName(),
                                    null, github.getOwnerLogin(), branches)));
                });

    }
}
