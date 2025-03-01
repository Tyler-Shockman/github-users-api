package org.branch.github_users_api.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GitHubUser implements Serializable {
    private String username;
    private String displayName;
    private String avatarUrl;
    private String geoLocation;
    private String email;
    private String overviewUrl;
    private LocalDateTime createdAt;
    private List<GitHubRepo> repos;
}
