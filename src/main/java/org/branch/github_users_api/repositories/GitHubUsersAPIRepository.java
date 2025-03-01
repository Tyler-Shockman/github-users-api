package org.branch.github_users_api.repositories;

import org.branch.github_users_api.domain.entities.GitHubRepo;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.branch.github_users_api.mappers.GitHubRepoMapper;
import org.branch.github_users_api.mappers.GitHubUserMapper;
import org.branch.github_users_api.repositories.dtos.GitHubAPIReposResponse;
import org.branch.github_users_api.repositories.dtos.GitHubAPIUserResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Repository
public class GitHubUsersAPIRepository {

    private final RestClient gitHubClient;
    private final GitHubRepoMapper gitHubRepoMapper;
    private final GitHubUserMapper gitHubUserMapper;

    public GitHubUsersAPIRepository(
            @Qualifier("gitHubUsersAPIRestClient") RestClient gitHubClient,
            GitHubRepoMapper gitHubRepoMapper,
            GitHubUserMapper gitHubUserMapper
    ) {
        this.gitHubClient = gitHubClient;
        this.gitHubRepoMapper = gitHubRepoMapper;
        this.gitHubUserMapper = gitHubUserMapper;
    }

    @Cacheable("githubUsersCache")
    public Optional<GitHubUser> findByUsername(String username) {
        GitHubAPIUserResponse userResponse = this.fetchGitHubUsers(username);
        assert userResponse != null;

        GitHubAPIReposResponse[] reposResponse = this.fetchGitHubRepos(username);
        assert reposResponse != null;

        List<GitHubRepo> repos = gitHubRepoMapper.fromGitHubAPIRepoResponse(reposResponse);

        return Optional.ofNullable(gitHubUserMapper.fromGitHubAPIUserResponse(userResponse, repos));
    }

    private GitHubAPIUserResponse fetchGitHubUsers(String username) {
        return gitHubClient.get()
                .uri("/{username}", username)
                .retrieve()
                .body(GitHubAPIUserResponse.class);
    }

    private GitHubAPIReposResponse[] fetchGitHubRepos(String username) {
        return gitHubClient.get()
                .uri("/{username}/repos", username)
                .retrieve()
                .body(GitHubAPIReposResponse[].class);
    }
}
