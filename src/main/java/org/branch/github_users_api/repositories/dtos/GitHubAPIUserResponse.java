package org.branch.github_users_api.repositories.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
record GitHubAPIUserResponse(
        String login,
        String avatarUrl,
        String htmlUrl,
        String name,
        String location,
        String email,
        LocalDateTime createdAt
) {}
