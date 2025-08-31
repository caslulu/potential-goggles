package br.com.caslulu.academia_api.auth;

import br.com.caslulu.academia_api.aluno.Aluno;
import br.com.caslulu.academia_api.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin()
public class AuthenticationController{
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService){
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  public record LoginRequestDTO(String email, String senha){}
  public record LoginResponseDTO(String token){}


  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> efetuarLogin(@RequestBody LoginRequestDTO dados){
    var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email, dados.senha());

    var authentication = authenticationManager.authenticate(authenticationToken);

    var alunoAutenticado = (Aluno) authentication.getPrincipal();

    var tokenJWT = tokenService.gerarToken(alunoAutenticado);

    return ResponseEntity.ok(new LoginResponseDTO(tokenJWT));
  }

}
