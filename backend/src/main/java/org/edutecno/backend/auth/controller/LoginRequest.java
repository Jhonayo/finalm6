package org.edutecno.backend.auth.controller;

public record LoginRequest(
        String email,
        String password
) {
}
