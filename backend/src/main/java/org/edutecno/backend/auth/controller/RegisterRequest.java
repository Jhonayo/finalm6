package org.edutecno.backend.auth.controller;

public record RegisterRequest(

        String email,
        String password,
        String name
) {
}
