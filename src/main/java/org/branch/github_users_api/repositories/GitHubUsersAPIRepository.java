package org.branch.github_users_api.repositories;

import org.branch.github_users_api.domain.entities.GitHubUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GitHubUsersAPIRepository {

    public Optional<GitHubUser> findByUsername(String username) {
        return Optional.ofNullable(GitHubUser.builder()
                .username(username)
                .build());
    }

}
