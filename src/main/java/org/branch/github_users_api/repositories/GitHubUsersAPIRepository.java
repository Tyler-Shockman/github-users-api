package org.branch.github_users_api.repositories;

import org.branch.github_users_api.domain.entities.GitHubRepo;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.branch.github_users_api.repositories.dtos.GitHubAPIReposResponse;
import org.branch.github_users_api.repositories.dtos.GitHubAPIUserResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class GitHubUsersAPIRepository {

    private final RestClient gitHubClient;

    public GitHubUsersAPIRepository(
            @Qualifier("gitHubUsersAPIRestClient") RestClient gitHubClient
    ) {
        this.gitHubClient = gitHubClient;
    }

    @Cacheable("githubUsersCache")
    public Optional<GitHubUser> findByUsername(String username) {
        GitHubAPIUserResponse userResponse = gitHubClient.get()
                .uri("/{username}", username)
                .retrieve()
                .body(GitHubAPIUserResponse.class);
        assert userResponse != null;

        GitHubAPIReposResponse[] reposResponse = gitHubClient.get()
                .uri("/{username}/repos", username)
                .retrieve()
                .body(GitHubAPIReposResponse[].class);
        assert reposResponse != null;

        List<GitHubRepo> mappedRepos = Arrays.stream(reposResponse)
                .map(repo ->
                        GitHubRepo.builder()
                                .name(repo.name())
                                .url(repo.htmlUrl())
                                .build()).
                toList();


        return Optional.ofNullable(GitHubUser.builder()
                .username(username)
                        .displayName(userResponse.name())
                        .avatarUrl(userResponse.avatarUrl())
                        .geoLocation(userResponse.location())
                        .email(userResponse.email())
                        .overviewUrl(userResponse.htmlUrl())
                        .createdAt(userResponse.createdAt())
                        .repos(mappedRepos)
                .build());
    }

}
