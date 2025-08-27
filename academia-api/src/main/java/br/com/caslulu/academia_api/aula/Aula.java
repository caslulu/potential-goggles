package br.com.caslulu.academia_api.aula;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Aula{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String instrutor;
  private String horario;
  private int capacidade;
  
  public Aula(){
  }

  public Long getId(){
    return this.id;
  }

  public String getNome(){
    return this.nome;
  }

  public String getInstrutor(){
    return this.instrutor;
  }

  public String getHorario(){
    return this.horario;
  }

  public int getCapacidade(){
    return this.capacidade;
  }

  public Long setId(Long novoId){
    return this.id = novoId;
  }

  public String setNome(String novoNome){
    return this.nome = novoNome;
  }

  public String setInstrutor(String novoInstrutor){
    return this.instrutor = novoInstrutor;
  }

  public String setHorario(String novoHorario){
    return this.horario = novoHorario;
  }

  public int setCapacidade(int novaCapacidade){
    return this.capacidade = novaCapacidade;
  }


  
}
