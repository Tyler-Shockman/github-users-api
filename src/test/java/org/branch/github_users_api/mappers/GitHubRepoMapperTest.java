package org.branch.github_users_api.mappers;

import org.branch.github_users_api.TestUtils;
import org.branch.github_users_api.domain.dtos.GitHubAPIReposResponse;
import org.branch.github_users_api.domain.dtos.GitHubUserDTO;
import org.branch.github_users_api.domain.entities.GitHubRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitHubRepoMapperTest {

    GitHubRepoMapper underTest = new GitHubRepoMapper();

    @Test
    void shouldMapFromGitHubApiRepoResponse() {
        GitHubAPIReposResponse gitHubAPIReposResponse = TestUtils.createGitHubAPIReposResponse("1");

        GitHubRepo result = underTest.fromGitHubAPIRepoResponse(gitHubAPIReposResponse);

        assertNotNull(result);
        GitHubRepo expect = TestUtils.createGitHubRepo("1");
        assertEquals(expect, result);
    }

    @Test
    void shouldMapFromManyGitHubApiRepoResponse() {
        GitHubAPIReposResponse[] gitHubAPIReposResponses = new GitHubAPIReposResponse[]{
                TestUtils.createGitHubAPIReposResponse("2"),
                TestUtils.createGitHubAPIReposResponse("3")
        };

        List<GitHubRepo> result = underTest.fromGitHubAPIRepoResponse(gitHubAPIReposResponses);

        assertNotNull(result);
        assertEquals(2, result.size());
        GitHubRepo expect1 = TestUtils.createGitHubRepo("2");
        assertEquals(expect1, result.getFirst());
        GitHubRepo expect2 = TestUtils.createGitHubRepo("3");
        assertEquals(expect2, result.getLast());
    }

    @Test
    void shouldMapToGitHubUsersDTORepoDTO() {
        GitHubRepo gitHubRepo = TestUtils.createGitHubRepo("4");

        GitHubUserDTO.RepoDTO result = underTest.toGitHubRepoDTO(gitHubRepo);

        assertNotNull(result);
        GitHubUserDTO.RepoDTO expect = TestUtils.createGitHubUserDTORepoDTO("4");
        assertEquals(expect, result);
    }

    @Test
    void shouldMapManyToGitHubUsersDTORepoDTO() {
        List<GitHubRepo> gitHubRepoList = List.of(
                TestUtils.createGitHubRepo("5"),
                TestUtils.createGitHubRepo("6")
        );

        List<GitHubUserDTO.RepoDTO> result = underTest.toGitHubRepoDTO(gitHubRepoList);

        assertNotNull(result);
        assertEquals(2, result.size());
        GitHubUserDTO.RepoDTO expect1 = TestUtils.createGitHubUserDTORepoDTO("5");
        assertEquals(expect1, result.getFirst());
        GitHubUserDTO.RepoDTO expect2 = TestUtils.createGitHubUserDTORepoDTO("6");
        assertEquals(expect2, result.getLast());
    }
}