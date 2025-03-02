package org.branch.github_users_api.services;

import org.branch.github_users_api.TestUtils;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.branch.github_users_api.repositories.GitHubUsersAPIRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GitHubUsersServiceTest {

    @Mock
    private GitHubUsersAPIRepository repository;

    @InjectMocks
    private GitHubUsersService underTest;

    @Test
    void shouldFindByUsername() {
        GitHubUser user = TestUtils.createGitHubUser("1");
        when(repository.findByUsername(eq(user.getUsername()))).thenReturn(Optional.of(user));

        Optional<GitHubUser> result = underTest.findByUsername(user.getUsername());

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

    }
}