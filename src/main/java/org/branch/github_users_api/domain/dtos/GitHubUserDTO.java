package org.branch.github_users_api.domain.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GitHubUserDTO(
        String username,
        String displayName,
        String avatar,
        String geoLocation,
        String email,
        String url,
        String createdAt,
        RepoDTO repos
) {
    public record RepoDTO(
            String name,
            String url
    ) {}
}
