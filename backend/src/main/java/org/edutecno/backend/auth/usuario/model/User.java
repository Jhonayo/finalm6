package org.edutecno.backend.auth.usuario.model;


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
  @GeneratedValue
  private Long id;

  private String name;

  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Token> tokens;

}
