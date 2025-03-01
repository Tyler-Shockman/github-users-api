package org.branch.github_users_api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class GitHubRepo implements Serializable {
    private String name;
    private String url;
}
