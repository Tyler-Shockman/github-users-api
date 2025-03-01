package org.branch.github_users_api.controllers;

import lombok.AllArgsConstructor;
import org.branch.github_users_api.domain.dtos.GitHubUserDTO;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.branch.github_users_api.services.GitHubUsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/github-users")
@AllArgsConstructor
public final class GitHubUsersController {

    private final GitHubUsersService gitHubUsersService;

    @GetMapping("/{username}")
    public ResponseEntity<GitHubUserDTO> getGitHubUser(@PathVariable String username) {
        GitHubUser foundGithubUser = gitHubUsersService.findByUsername(username);

        return ResponseEntity.ok(new GitHubUserDTO(
                foundGithubUser.getUsername(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ));
    }
}
