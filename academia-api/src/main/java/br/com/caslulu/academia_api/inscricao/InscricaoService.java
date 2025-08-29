package br.com.caslulu.academia_api.inscricao;

import br.com.caslulu.academia_api.aula.Aula;
import br.com.caslulu.academia_api.aula.AulaRepository;
import br.com.caslulu.academia_api.aluno.AlunoRepository;
import br.com.caslulu.academia_api.aluno.Aluno;

import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
@Service
public class InscricaoService{
  private final InscricaoRepository inscricaoRepository;
  private final AulaRepository aulaRepository;
  private final AlunoRepository alunoRepository;

  public InscricaoService(AulaRepository aulaRepository, AlunoRepository alunoRepository, InscricaoRepository inscricaoRepository){
     this.aulaRepository = aulaRepository;
     this.inscricaoRepository = inscricaoRepository;
     this.alunoRepository = alunoRepository;
  }

  public Inscricao realizarInscricao(Long alunoId, Long aulaId){
    Optional <Aula> aulaOpcional = this.aulaRepository.findById(aulaId);
    Optional <Aluno> alunoOpcional = this.alunoRepository.findById(alunoId);
    if(aulaOpcional.isPresent() && alunoOpcional.isPresent()){
      Aula aulaEncontrada = aulaOpcional.get();
      Aluno alunoEncontrado = alunoOpcional.get();
      int inscricoesAtuais = inscricaoRepository.countByAulaId(aulaId);
      if (inscricoesAtuais >= aulaEncontrada.getCapacidade())
      {
        throw new RuntimeException("Não foi possivel Inscrever, aula esta lotada");
      }
      Inscricao novaInscricao = new Inscricao();
      novaInscricao.setAluno(alunoEncontrado);
      novaInscricao.setAula(aulaEncontrada);
      novaInscricao.setData(LocalDateTime.now());
      return this.inscricaoRepository.save(novaInscricao);
    }
    throw new RuntimeException("Aluna ou Aula não encontrada pelo Id");
  }
}
