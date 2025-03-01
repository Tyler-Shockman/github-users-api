package org.branch.github_users_api.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class APIRestClientConfiguration {

    @Bean("gitHubUsersAPIRestClient")
    RestClient gitHubUsersAPIRestClient(
            @Value("${clients.github.users.personal-access-token:#{null}}")
            String personalAccessToken
    ) {
        RestClient.Builder restClientBuilder = RestClient.builder().baseUrl("https://api.github.com/users");
        if (personalAccessToken != null) restClientBuilder.defaultHeader("Authorization", "Bearer " + personalAccessToken);
        return restClientBuilder.build();
    }

}