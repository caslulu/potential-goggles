package br.com.caslulu.academia_api.inscricao;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import br.com.caslulu.academia_api.aula.Aula;
import br.com.caslulu.academia_api.aluno.Aluno;

import jakarta.persistence.ManyToOne;
import java.util.List;
import java.time.LocalDateTime;

@Entity
public class Inscricao{
  
  public Inscricao(){
    
  }

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long Id;
  
  @ManyToOne
  private Aluno aluno;

  @ManyToOne
  private Aula aula;

  private LocalDateTime dataInscricao;

  public Long getId(){
    return this.Id;
  }

  public Aluno getAluno(){
    return this.aluno;
  }

  public Aula getAula(){
    return this.aula;
  }
  
  public LocalDateTime getDataInscricao(){
    return this.dataInscricao;
  }

  public Aluno setAluno(Aluno alunoNovo){
    this.aluno = alunoNovo;
    return alunoNovo;
  }
  
  public Aula setAula(Aula aulaNova){
    this.aula = aulaNova;
    return aulaNova;
  }

  public LocalDateTime setDataInscricao(LocalDateTime dataNova){
    this.dataInscricao = dataNova;
    return dataNova;
  }
}
