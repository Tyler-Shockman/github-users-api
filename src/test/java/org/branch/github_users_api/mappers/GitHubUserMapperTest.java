package org.branch.github_users_api.mappers;

import org.branch.github_users_api.TestUtils;
import org.branch.github_users_api.domain.dtos.GitHubAPIUserResponse;
import org.branch.github_users_api.domain.dtos.GitHubUserDTO;
import org.branch.github_users_api.domain.entities.GitHubRepo;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GitHubUserMapperTest {

    @Mock
    private GitHubRepoMapper gitHubRepoMapper;

    @InjectMocks
    private GitHubUserMapper underTest;

    @Test
    void shouldMapFromGitHubAPIUserResponse() {
        GitHubAPIUserResponse response = TestUtils.createGitHubAPIUserResponse("1");

        GitHubUser gitHubUser = underTest.fromGitHubAPIUserResponse(response);

        assertNotNull(gitHubUser);
        GitHubUser expected = TestUtils.createGitHubUser("1");
        expected.setRepos(null);
        assertEquals(expected, gitHubUser);
    }

    @Test
    void shouldMapFromGitHubAPIUserResponseWithRepos() {
        GitHubAPIUserResponse response = TestUtils.createGitHubAPIUserResponse("2");
        List<GitHubRepo> repos = List.of(TestUtils.createGitHubRepo("2"));

        GitHubUser gitHubUser = underTest.fromGitHubAPIUserResponse(response, repos);

        assertNotNull(gitHubUser);
        GitHubUser expectedUser = TestUtils.createGitHubUser("2");
        expectedUser.setRepos(repos);
        assertEquals(expectedUser, gitHubUser);
    }

    @Test
    void shouldMapToGitHubUserDTO() {
        GitHubUser gitHubUser = TestUtils.createGitHubUser("3");
        when(gitHubRepoMapper.toGitHubRepoDTO(eq(gitHubUser.getRepos()))).thenReturn(
                List.of(TestUtils.createGitHubUserDTORepoDTO("3"))
        );

        GitHubUserDTO githubUserDTO = underTest.toGitHubUserDTO(gitHubUser);

        assertNotNull(githubUserDTO);
        assertEquals(TestUtils.createGitHubUserDTO("3"), githubUserDTO);
    }
}