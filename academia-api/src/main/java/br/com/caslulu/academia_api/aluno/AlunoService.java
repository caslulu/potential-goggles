package br.com.caslulu.academia_api.aluno;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.caslulu.academia_api.infra.security.SecurityConfigurations;
import br.com.caslulu.academia_api.inscricao.InscricaoRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AlunoService{
  private final PasswordEncoder passEncoder;
  private final AlunoRepository repository;
  private final InscricaoRepository inscricaoRepository;

  public AlunoService(PasswordEncoder passEncoder, AlunoRepository repository, InscricaoRepository inscricaoRepository){
    this.passEncoder = passEncoder;
    this.repository = repository;
    this.inscricaoRepository = inscricaoRepository;
  }

  public Aluno criar(Aluno novoAluno) throws Exception{
    String senhaPura = novoAluno.getSenha();
    if (senhaPura == null || novoAluno.getNome() == null || novoAluno.getEmail() == null
      || senhaPura.isBlank() || novoAluno.getNome().isBlank() || novoAluno.getEmail().isBlank()){
      throw new IllegalArgumentException("Nome, Email e senha sao obrigatorios");
    }
    
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
      inscricaoRepository.deleteByAlunoId(id);
      repository.deleteById(id);
      return;
    }
    throw new RuntimeException("Aluno nao encontrado para o ID: " + id);
  }
}
