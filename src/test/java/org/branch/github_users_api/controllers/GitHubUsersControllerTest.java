package org.branch.github_users_api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.branch.github_users_api.TestUtils;
import org.branch.github_users_api.domain.dtos.GitHubUserDTO;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.branch.github_users_api.mappers.GitHubUserMapper;
import org.branch.github_users_api.services.GitHubUsersService;
import org.branch.github_users_api.services.RateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubUsersControllerTest {

    @Mock
    private GitHubUsersService gitHubUsersService;
    @Mock
    private GitHubUserMapper githubUserMapper;
    @Mock
    private RateLimiter rateLimiter;

    @InjectMocks
    private GitHubUsersController underTest;

    @BeforeEach
    void setUp() {
        when(rateLimiter.acquirePermit(anyString())).thenReturn(true);
    }

    @Test
    void shouldFindByUsername() {
        GitHubUser user = TestUtils.createGitHubUser("1");
        when(gitHubUsersService.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        GitHubUserDTO dto = TestUtils.createGitHubUserDTO("1");
        when(githubUserMapper.toGitHubUserDTO(user)).thenReturn(dto);

        ResponseEntity<GitHubUserDTO> result = underTest.getGitHubUser(
                user.getUsername(),
                Mockito.mock(HttpServletRequest.class)
        );

        assertNotNull(result);
        assertEquals(dto, result.getBody());
    }

    @Test
    void shouldReturn404WhenUserNotFound() {
        when(gitHubUsersService.findByUsername("not-a-user")).thenReturn(Optional.empty());

        ResponseEntity<GitHubUserDTO> result = underTest.getGitHubUser(
                "not-a-user",
                Mockito.mock(HttpServletRequest.class)
        );

        assertNotNull(result);
        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    void shouldReturn429WhenTheRateLimitIsReached() {
        when(rateLimiter.acquirePermit(anyString())).thenReturn(false);

        ResponseEntity<GitHubUserDTO> result = underTest.getGitHubUser(
                "test-user",
                Mockito.mock(HttpServletRequest.class)
        );

        assertNotNull(result);
        assertEquals(429, result.getStatusCode().value());
    }
}