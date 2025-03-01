package org.branch.github_users_api.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class AppRedisCacheConfiguration {

    @Bean
    public RedisCacheManagerBuilderCustomizer cacheManagerCustomizer(
            @Value("${cache.github.users.ttl-seconds:60}")
            int gitHubUsersCacheTtlSeconds
    ) {
        return (builder) -> builder.withCacheConfiguration(
                "githubUsersCache",
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(gitHubUsersCacheTtlSeconds))
        );
    }
}
