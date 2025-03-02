package org.branch.github_users_api.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterTest {

    @Test
    void shouldReturnTrueWhenTheLimitHasNotYetExceeded() {
        RateLimiter underTest = new RateLimiter(1, 10);

        boolean result = underTest.acquirePermit("my-token");

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenTheLimitHasYetExceeded() {
        RateLimiter underTest = new RateLimiter(1, 10);

        underTest.acquirePermit("my-token");
        boolean result = underTest.acquirePermit("my-token");

        assertFalse(result);
    }

    @Test
    void shouldCountTheLimitBasedOnToken() {
        RateLimiter underTest = new RateLimiter(1, 10);

        boolean result1 = underTest.acquirePermit("my-token");
        boolean result2 = underTest.acquirePermit("another-token");

        assertTrue(result1);
        assertTrue(result2);

        boolean result3 = underTest.acquirePermit("my-token");
        assertFalse(result3);

        boolean result4 = underTest.acquirePermit("a-third-token");
        assertTrue(result4);
    }
}