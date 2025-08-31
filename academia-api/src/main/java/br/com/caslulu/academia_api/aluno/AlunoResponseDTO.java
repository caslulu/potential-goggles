package br.com.caslulu.academia_api.aluno;


public record AlunoResponseDTO(Long id, String nome, String email){
    public AlunoResponseDTO(Aluno aluno){
      this(aluno.getId(), aluno.getNome(), aluno.getEmail());
    }
  }
