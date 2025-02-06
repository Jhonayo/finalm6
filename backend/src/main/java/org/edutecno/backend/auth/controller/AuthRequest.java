package org.edutecno.backend.auth.controller;

public record AuthRequest(
        String email,
        String password
) {
}
