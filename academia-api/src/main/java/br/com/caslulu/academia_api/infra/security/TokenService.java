package br.com.caslulu.academia_api.infra.security;

import br.com.caslulu.academia_api.aluno.Aluno;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService{
  @Value("${api.security.token.secret}")
  private String secret;

  public String gerarToken(Aluno aluno){
    try{
      Algorithm algoritmo = Algorithm.HMAC256(secret);
      return JWT.create().withIssuer("Academia API")
        .withSubject(aluno.getEmail())
        .withExpiresAt(gerarDataExpiracao())
        .sign(algoritmo);
    } catch (JWTCreationException exception){
        throw new RuntimeException("Erro ao gerar token", exception);
    }
  }
  private Instant gerarDataExpiracao(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
