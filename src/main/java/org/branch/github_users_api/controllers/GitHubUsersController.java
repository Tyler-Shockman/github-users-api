package org.branch.github_users_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/github-users")
public class GitHubUsersController {

    @GetMapping("/{username}")
    public String getGitHubUser(@PathVariable String username) {
        return username + " received";
    }
}
