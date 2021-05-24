package com.tui.github.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Branch {

    private String name;
    private String sha;

    @JsonProperty("commit")
    private void getCommitSha(Map<String,String> commit) {
        this.sha = commit.get("sha");
    }

}
