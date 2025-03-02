package org.branch.github_users_api;

import org.branch.github_users_api.domain.dtos.GitHubAPIReposResponse;
import org.branch.github_users_api.domain.dtos.GitHubAPIUserResponse;
import org.branch.github_users_api.domain.dtos.GitHubUserDTO;
import org.branch.github_users_api.domain.entities.GitHubRepo;
import org.branch.github_users_api.domain.entities.GitHubUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TestUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static GitHubUser createGitHubUser(String salt) {
        String saltToUse = getSaltToUser(salt);
        return new GitHubUser(
                "test-user" + saltToUse,
                "test-name" + saltToUse,
                "https://test.com/avatar" + saltToUse,
                "test-location" + saltToUse,
                "test-user" + saltToUse + "@test.com",
                "https://test.com/html" + saltToUse,
                LocalDateTime.of(2025, 3, 1, 0, 0, 0, 0),
                List.of(createGitHubRepo(salt))
        );
    }

    public static GitHubRepo createGitHubRepo(String salt) {
        String saltToUse = getSaltToUser(salt);
        return GitHubRepo.builder()
                .name("test-repo" + saltToUse)
                .url("https://test.com/repo/test-repo" + saltToUse)
                .build();
    }

    public static GitHubAPIUserResponse createGitHubAPIUserResponse(String salt) {
        String saltToUse = getSaltToUser(salt);
        return new GitHubAPIUserResponse(
                "test-user" + saltToUse,
                "https://test.com/avatar" + saltToUse,
                "https://test.com/html" + saltToUse,
                "test-name" + saltToUse,
                "test-location" + saltToUse,
                "test-user" + saltToUse + "@test.com",
                LocalDateTime.of(2025, 3, 1, 0, 0, 0, 0)
        );
    }

    public static GitHubAPIReposResponse createGitHubAPIReposResponse(String salt) {
        String saltToUse = getSaltToUser(salt);
        return new GitHubAPIReposResponse(
                "test-repo" + saltToUse,
                "https://test.com/repo/test-repo" + saltToUse
        );
    }

    public static GitHubUserDTO createGitHubUserDTO(String salt) {
        String saltToUse = getSaltToUser(salt);
        return new GitHubUserDTO(
                "test-user" + saltToUse,
                "test-name" + saltToUse,
                "https://test.com/avatar" + saltToUse,
                "test-location" + saltToUse,
                "test-user" + saltToUse + "@test.com",
                "https://test.com/html" + saltToUse,
                formatter.format(LocalDateTime.of(2025, 3, 1, 0, 0, 0, 0)),
                List.of(createGitHubUserDTORepoDTO(salt))
        );
    }

    public static GitHubUserDTO.RepoDTO createGitHubUserDTORepoDTO(String salt) {
        String saltToUse = getSaltToUser(salt);
        return new GitHubUserDTO.RepoDTO(
                "test-repo" + saltToUse,
                "https://test.com/repo/test-repo" + saltToUse
        );
    }

    private static String getSaltToUser(String salt) {
        if (!salt.isEmpty()) return  "-" + salt;
        else return "";
    }
}
