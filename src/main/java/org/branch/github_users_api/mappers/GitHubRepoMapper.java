package org.branch.github_users_api.mappers;

import org.branch.github_users_api.domain.dtos.GitHubUserDTO;
import org.branch.github_users_api.domain.entities.GitHubRepo;
import org.branch.github_users_api.repositories.dtos.GitHubAPIReposResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GitHubRepoMapper {
    public GitHubRepo fromGitHubAPIRepoResponse(GitHubAPIReposResponse gitHubAPIReposResponse) {
        return GitHubRepo.builder()
                .name(gitHubAPIReposResponse.name())
                .url(gitHubAPIReposResponse.htmlUrl())
                .build();
    }

    public List<GitHubRepo> fromGitHubAPIRepoResponse(GitHubAPIReposResponse[] gitHubAPIReposResponses) {
        return Arrays.stream(gitHubAPIReposResponses).map(this::fromGitHubAPIRepoResponse).toList();
    }

    public GitHubUserDTO.RepoDTO toGitHubRepoDTO(GitHubRepo gitHubRepo) {
        return new GitHubUserDTO.RepoDTO(
                gitHubRepo.getName(),
                gitHubRepo.getUrl()
        );
    }

    public List<GitHubUserDTO.RepoDTO> toGitHubRepoDTO(List<GitHubRepo> gitHubRepos) {
        return gitHubRepos.stream().map(this::toGitHubRepoDTO).toList();
    }
    }
