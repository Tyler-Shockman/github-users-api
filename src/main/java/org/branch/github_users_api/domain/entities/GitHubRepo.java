package org.branch.github_users_api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GitHubRepo {
    private String name;
    private String url;
}
