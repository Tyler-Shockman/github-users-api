package org.branch.github_users_api.services;

import org.branch.github_users_api.domain.entities.GitHubUser;
import org.springframework.stereotype.Service;

@Service
public final class GitHubUsersService {

    public GitHubUser findByUsername(String username) {
        return GitHubUser.builder()
                .username(username)
                .build();
    }

}
