package org.edutecno.backend.auth.usuario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
/*
    private final UserRepository userRepository;

    @GetMapping
    public List<UserResponse> changePassword() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getName(), user.getEmail()))
                .toList();
    }*/
}
