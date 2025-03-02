package org.branch.github_users_api.repositories;

import org.branch.github_users_api.TestUtils;
import org.branch.github_users_api.domain.dtos.GitHubAPIReposResponse;
import org.branch.github_users_api.domain.dtos.GitHubAPIUserResponse;
import org.branch.github_users_api.domain.entities.GitHubRepo;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.branch.github_users_api.mappers.GitHubRepoMapper;
import org.branch.github_users_api.mappers.GitHubUserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitHubUsersAPIRepositoryTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient gitHubClient;
    @Mock
    private GitHubUserMapper gitHubUserMapper;
    @Mock
    private GitHubRepoMapper gitHubRepoMapper;

    @InjectMocks
    GitHubUsersAPIRepository underTest;

    @Test
    void shouldFindAGitHubUserByUsername() {
        List<GitHubRepo> repos = List.of(TestUtils.createGitHubRepo("1"));
        GitHubUser user = TestUtils.createGitHubUser("1");
        user.setRepos(repos);

        GitHubAPIUserResponse userResponse = TestUtils.createGitHubAPIUserResponse("1");
        lenient().when(gitHubClient.get().uri("/{username}", user.getUsername()).retrieve().body(GitHubAPIUserResponse.class))
                .thenReturn(userResponse);

        GitHubAPIReposResponse[] reposResponses = new GitHubAPIReposResponse[] {TestUtils.createGitHubAPIReposResponse("1")};
        lenient().when(gitHubClient.get().uri("/{username}/repos", user.getUsername()).retrieve().body(GitHubAPIReposResponse[].class))
                .thenReturn(reposResponses);

        when(gitHubRepoMapper.fromGitHubAPIRepoResponse(any(GitHubAPIReposResponse[].class)))
                .thenReturn(repos);

        when(gitHubUserMapper.fromGitHubAPIUserResponse(any(GitHubAPIUserResponse.class), eq(repos)))
                .thenReturn(user);

        Optional<GitHubUser> result = underTest.findByUsername(user.getUsername());

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(gitHubUserMapper).fromGitHubAPIUserResponse(eq(userResponse), eq(repos));
        verify(gitHubRepoMapper).fromGitHubAPIRepoResponse(eq(reposResponses));
    }
}