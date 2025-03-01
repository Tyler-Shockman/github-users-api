package org.branch.github_users_api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.branch.github_users_api.domain.dtos.GitHubUserDTO;
import org.branch.github_users_api.domain.entities.GitHubUser;
import org.branch.github_users_api.mappers.GitHubUserMapper;
import org.branch.github_users_api.services.GitHubUsersService;
import org.branch.github_users_api.services.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/github-users")
@AllArgsConstructor
public final class GitHubUsersController {

    private final GitHubUsersService gitHubUsersService;
    private final GitHubUserMapper gitHubUserMapper;
    private final RateLimiter rateLimiter;

    @GetMapping("/{username}")
    public ResponseEntity<GitHubUserDTO> getGitHubUser(@PathVariable String username, HttpServletRequest request) {
        if (!rateLimiter.acquirePermit("getGitHubUsers-" + request.getRemoteAddr())) return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        Optional<GitHubUser> foundGithubUser = gitHubUsersService.findByUsername(username);

        return foundGithubUser.map(gitHubUser ->
                ResponseEntity.ok().body(gitHubUserMapper.toGitHubUserDTO(gitHubUser))
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
