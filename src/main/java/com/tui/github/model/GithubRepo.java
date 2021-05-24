package com.tui.github.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubRepo {

    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("fork")
    private String isFork;
    private String ownerLogin;
    private List<Branch> branch;

    @JsonProperty("owner")
    private void getOwnerLogin(Map<String,String> commit) {
        this.ownerLogin = commit.get("login");
    }
}
