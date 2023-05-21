package com.octadev.nutria.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.octadev.nutria.models.Credential;
import com.octadev.nutria.models.User;
import com.octadev.nutria.models.Token;
import com.octadev.nutria.repository.UserRepository;


@Service
public class TokenService {

  @Autowired
  UserRepository repository;

  public Token generateToken(Credential credential) {
    Algorithm alg = Algorithm.HMAC256("hypersecret");
    var jwt = JWT.create()
        .withSubject(credential.email())
        .withIssuer("NutrIA")
        .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
        .sign(alg);
    return new Token(jwt, "JWT", "Bearer");
  }

  public User validate(String token) {
    Algorithm alg = Algorithm.HMAC256("meusecret");
    var email = JWT.require(alg)
        .withIssuer("NutrIA")
        .build()
        .verify(token)
        .getSubject();

    return repository.findByEmail(email).orElseThrow(() -> new JWTVerificationException("usuario nao encontrado"));
  }
}
