package org.branch.github_users_api.controllers.exception_handlers;

import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.java.Log;
import org.branch.github_users_api.controllers.GitHubUsersController;
import org.branch.github_users_api.domain.dtos.GetGitHubUserErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Optional;

@ControllerAdvice(assignableTypes = GitHubUsersController.class)
@Log
public class GitHubUsersControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = HttpClientErrorException.NotFound.class)
    public ResponseEntity<GetGitHubUserErrorResponse> handleGitHubReturnedNotFound() {
        return ResponseEntity.of(Optional.of(new GetGitHubUserErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "We were unable to locate a GitHub user by that username. Please double check " +
                        "the username you provided. If you can confirm the username is correct and the " +
                        "user exists, please contact support."
        )));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HandlerMethodValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleRequestValidationFailed() {
        return ResponseEntity.of(Optional.of(new GetGitHubUserErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "The username provided is not a valid username. Usernames should" +
                        "consist of alphanumeric characters & dashes (-). They cannot exceed 39 characters" +
                        "in length, cannot contain two dashes (-) in a row, and cannot start or end with a dash (-)."
        )));
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(value = {ConnectTimeoutException.class, ReadTimeoutException.class})
    public ResponseEntity<GetGitHubUserErrorResponse> handleFailedToConnectToGitHub(ConnectTimeoutException ex) {
        log.severe("Failed to get response from GitHub. Timeout reached. Error: " + ex);
        return ResponseEntity.of(Optional.of(new GetGitHubUserErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "An error occurred when attempting to connect to GitHub and no response was ever " +
                        "received. Please try again later. If this issue persists, please contact support."
        )));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<GetGitHubUserErrorResponse> handleUnexpectedException(Exception ex) {
        log.severe("Unexpected exception encountered: " + ex);
        return ResponseEntity.of(Optional.of(new GetGitHubUserErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "We encountered an error on our side. Please try again. If this issue " +
                        "persists, please contact support."
        )));
    }
}
