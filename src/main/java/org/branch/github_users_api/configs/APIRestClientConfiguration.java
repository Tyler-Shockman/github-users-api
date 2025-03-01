package org.branch.github_users_api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class APIRestClientConfiguration {

    @Bean("gitHubUsersAPIRestClient")
    RestClient gitHubUsersAPIRestClient() {
        return RestClient.builder().baseUrl("https://api.github.com/users").build();
    }

}