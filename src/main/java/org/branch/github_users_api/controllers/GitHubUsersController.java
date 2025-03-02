package org.branch.github_users_api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    static final String GITHUB_USERNAME_FORMAT = "^((?!-)(?!.*--)[A-Za-z0-9-]+(?<!-))$";

    private final GitHubUsersService gitHubUsersService;
    private final GitHubUserMapper gitHubUserMapper;
    private final RateLimiter rateLimiter;

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GitHubUserDTO> getGitHubUser(
            @PathVariable("username")
            @Size(min = 1, max = 39)
            @Pattern(regexp = GITHUB_USERNAME_FORMAT)
            String username,
            HttpServletRequest request
    ) {
        if (!rateLimiter.acquirePermit("getGitHubUsers-" + request.getRemoteAddr())) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        Optional<GitHubUser> foundGithubUser = gitHubUsersService.findByUsername(username);

        return foundGithubUser.map(gitHubUser ->
                ResponseEntity.ok().body(gitHubUserMapper.toGitHubUserDTO(gitHubUser))
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
