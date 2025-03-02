package org.branch.github_users_api.mappers;

import lombok.AllArgsConstructor;
import org.branch.github_users_api.domain.dtos.GitHubUserDTO;
import org.branch.github_users_api.domain.entities.GitHubRepo;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.branch.github_users_api.domain.dtos.GitHubAPIUserResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@AllArgsConstructor
public class GitHubUserMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private GitHubRepoMapper gitHubRepoMapper;

    public GitHubUser fromGitHubAPIUserResponse(GitHubAPIUserResponse gitHubAPIUserResponse) {
        return GitHubUser.builder()
                .username(gitHubAPIUserResponse.login())
                .displayName(gitHubAPIUserResponse.name())
                .avatarUrl(gitHubAPIUserResponse.avatarUrl())
                .geoLocation(gitHubAPIUserResponse.location())
                .email(gitHubAPIUserResponse.email())
                .overviewUrl(gitHubAPIUserResponse.htmlUrl())
                .createdAt(gitHubAPIUserResponse.createdAt())
                .build();
    }

    public GitHubUser fromGitHubAPIUserResponse(GitHubAPIUserResponse gitHubAPIUserResponse, List<GitHubRepo> repos) {
        GitHubUser user = this.fromGitHubAPIUserResponse(gitHubAPIUserResponse);
        user.setRepos(repos);
        return user;
    }

    public GitHubUserDTO toGitHubUserDTO(GitHubUser gitHubUser) {
        return new GitHubUserDTO(
                gitHubUser.getUsername(),
                gitHubUser.getDisplayName(),
                gitHubUser.getAvatarUrl(),
                gitHubUser.getGeoLocation(),
                gitHubUser.getEmail(),
                gitHubUser.getOverviewUrl(),
                gitHubUser.getCreatedAt().format(formatter),
                gitHubRepoMapper.toGitHubRepoDTO(gitHubUser.getRepos())
        );
    }
}
