package org.branch.github_users_api.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
class APIRestClientConfiguration {

    @Bean("gitHubUsersAPIRestClient")
    RestClient gitHubUsersAPIRestClient(
            @Value("${clients.github.users.base-url:https://api.github.com/users}")
            String baseUrl,
            @Value("${clients.github.users.personal-access-token:#{null}}")
            String personalAccessToken,
            @Value("${clients.github.users.connection-timeout-millis:#{1000}}")
            int connectionTimeout,
            @Value("${clients.github.users.read-timeout-millis:#{5000}}")
            int readTimeout
    ) {
        RestClient.Builder restClientBuilder = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(
                        ClientHttpRequestFactoryBuilder.detect().build(
                                ClientHttpRequestFactorySettings.defaults()
                                        .withConnectTimeout(Duration.ofMillis(connectionTimeout))
                                        .withReadTimeout(Duration.ofMillis(readTimeout)))
                );
        if (personalAccessToken != null) restClientBuilder.defaultHeader("Authorization", "Bearer " + personalAccessToken);
        return restClientBuilder.build();
    }

}