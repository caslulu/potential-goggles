package br.com.caslulu.academia_api.aluno;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.caslulu.academia_api.infra.security.SecurityConfigurations;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService{
  private final PasswordEncoder passEncoder;
  private final AlunoRepository repository;

  public AlunoService(PasswordEncoder passEncoder, AlunoRepository repository){
    this.passEncoder = passEncoder;
    this.repository = repository;
  }

  public Aluno criar(Aluno novoAluno){
    String senhaPura = novoAluno.getSenha();
    
    String senhaCriptografada = passEncoder.encode(senhaPura);

    novoAluno.setSenha(senhaCriptografada);
    novoAluno.setRole("ALUNO");
    return repository.save(novoAluno);
  }

  public List<AlunoResponseDTO> verTodos(){
    List<Aluno> alunos = repository.findAll();
    return alunos.stream()
      .map(AlunoResponseDTO::new)
      .toList();
  }

  public AlunoResponseDTO buscarPorId(Long id){
    return repository.findById(id)
      .map(AlunoResponseDTO::new)
        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
  }

  public AlunoResponseDTO atualizar(Long id, Aluno alunoModificado){
    Optional<Aluno> alunoOpcional = repository.findById(id);
    if(alunoOpcional.isPresent()){
      Aluno alunoExistente = alunoOpcional.get();
      alunoExistente.setNome(alunoModificado.getNome());
      alunoExistente.setEmail(alunoModificado.getEmail());
      Aluno alunoSalvo = repository.save(alunoExistente);
      return new AlunoResponseDTO(alunoSalvo);
    }
    throw new RuntimeException("Não foi possivel encontrar o aluno para atualizar");
  }

  public void deletar(Long id){
    if(repository.existsById(id)){
      repository.deleteById(id);
      return;
    }
    throw new RuntimeException("Aluno nao encontrado para o ID: " + id);
  }
}
