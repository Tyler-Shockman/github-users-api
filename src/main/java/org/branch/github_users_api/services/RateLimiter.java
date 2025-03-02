package org.branch.github_users_api.services;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@Log
public class RateLimiter {
    private final Map<String, Bucket> buckets = new HashMap<>();
    private final Bandwidth bandwidth;

    public RateLimiter(
            @Value("${rate-limit.limit:60}")
            final int limit,
            @Value("${rate-limit.limit-seconds:60}")
            final int limitSeconds
            ) {
        this.bandwidth = Bandwidth.classic(limit, Refill.intervally(limit, Duration.ofSeconds(limitSeconds)));
    }

    public boolean acquirePermit(String token) {
        try {
            if (!buckets.containsKey(token)) {
                this.buckets.put(token, Bucket.builder().addLimit(this.bandwidth).build());
            }
            return this.buckets.get(token).tryConsume(1);
        } catch (Exception e) {
            log.severe("Failed to acquire permit for token ending in: " +
                    token.substring(token.length()-4) +
                    ". Rate limiter is not working. Error message: " + e.getMessage()
            );
        }
        return true;
    }
}
