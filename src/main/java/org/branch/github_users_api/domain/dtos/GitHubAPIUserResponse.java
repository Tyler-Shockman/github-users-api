package org.branch.github_users_api.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GitHubAPIUserResponse(
        @Null()
        String login,
        String avatarUrl,
        String htmlUrl,
        String name,
        String location,
        @Email
        String email,
        @Past
        LocalDateTime createdAt
) {}
