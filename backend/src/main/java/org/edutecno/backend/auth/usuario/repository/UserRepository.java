package org.edutecno.backend.auth.usuario.repository;


import org.edutecno.backend.auth.usuario.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}
