package br.com.caslulu.academia_api.aluno;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Aluno implements UserDetails{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String email;
  private String senha;
  private String role;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities(){
    if ("ADMIN".equalsIgnoreCase(this.role)){
      return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    }
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword(){
    return this.senha;
  }

  @Override 
  public String getUsername(){
    return this.email;
  }
  
  @Override
  public boolean isAccountNonExpired() { return true;}

  @Override
  public boolean isAccountNonLocked() { return true;}

  @Override
  public boolean isCredentialsNonExpired() { return true;}

  @Override
  public boolean isEnabled() { return true;}

  public Aluno() {
  }

  public Aluno(String nome, String email) {
    this.nome = nome;
    this.email = email;
  }

  public String getNome() {
    return this.nome;
  }

  public String getEmail() {
    return this.email;
  }

  public Long getId() {
    return this.id;
  }

  public String getSenha(){
    return this.senha;
  }

  public String getRole(){
    return this.role;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setSenha(String senha){
    this.senha = senha;
  }
  
  public void setRole(String role){
    this.role = role;
  }
}
