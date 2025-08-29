package br.com.caslulu.academia_api.aula;

import br.com.caslulu.academia_api.inscricao.InscricaoRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AulaService{
  private final AulaRepository aulaRepository;
  private final InscricaoRepository inscricaoRepository;

  public AulaService(AulaRepository aulaRepository, InscricaoRepository inscricaoRepository){
    this.aulaRepository = aulaRepository;
    this.inscricaoRepository = inscricaoRepository;
  }

  public List<AulaResponseDTO> listarAulasComVagas(){
    List<Aula> aulas = aulaRepository.findAll();
    return aulas.stream().map(aula-> {
      int inscritos = inscricaoRepository.countByAulaId(aula.getId());
      return new AulaResponseDTO(
          aula.getId(),
          aula.getNome(),
          aula.getInstrutor(),
          aula.getHorario(),
          aula.getCapacidade(),
          inscritos 
        );
    }).toList();
  }
}
