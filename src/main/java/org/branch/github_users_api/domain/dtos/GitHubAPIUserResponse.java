package org.branch.github_users_api.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GitHubAPIUserResponse(
        String login,
        String avatarUrl,
        String htmlUrl,
        String name,
        String location,
        String email,
        LocalDateTime createdAt
) {}
