package org.branch.github_users_api.domain.dtos;

public record GetGitHubUserErrorResponse(
        int status,
        String message
) {
}
