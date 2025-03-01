package org.branch.github_users_api.services;

import lombok.AllArgsConstructor;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.branch.github_users_api.repositories.GitHubUsersAPIRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public final class GitHubUsersService {

    private final GitHubUsersAPIRepository repository;

    public Optional<GitHubUser> findByUsername(String username) {
        return repository.findByUsername(username);
    }

}
