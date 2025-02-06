package org.edutecno.backend.usuario.model;


import java.util.List;

import jakarta.persistence.*;

import org.edutecno.backend.auth.model.Token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Token> tokens;

  @Enumerated(EnumType.STRING)
  private Role role;

}
