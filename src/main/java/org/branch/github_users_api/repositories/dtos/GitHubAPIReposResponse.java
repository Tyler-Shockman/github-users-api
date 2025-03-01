package org.branch.github_users_api.repositories.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record GitHubAPIReposResponse(
        String name,
        String htmlUrl
) {}
