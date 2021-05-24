package com.tui.github.service;

import com.tui.github.model.GithubRepo;
import reactor.core.publisher.Flux;

public interface IUserGithubService {

    Flux<GithubRepo> getUserGithubRepos(String userName);
}
