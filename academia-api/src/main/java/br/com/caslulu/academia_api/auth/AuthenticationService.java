package br.com.caslulu.academia_api.auth;


import br.com.caslulu.academia_api.aluno.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService{
  
  @Autowired
  private AlunoRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
    return repository.findByEmail(username);
  }
}
