package br.com.caslulu.academia_api.aula;

import br.com.caslulu.academia_api.inscricao.InscricaoRepository;

import java.util.Optional;
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
      int inscritos = this.inscricaoRepository.countByAulaId(aula.getId());
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


  public AulaResponseDTO buscarPorId(Long id) throws Exception{
    Optional<Aula> aulaOpcional = aulaRepository.findById(id);
    if(aulaOpcional.isPresent()){
      int inscritos = inscricaoRepository.countByAulaId(id);
      Aula aula = aulaOpcional.get();
      return new AulaResponseDTO(
          aula.getId(),
          aula.getNome(),
          aula.getInstrutor(),
          aula.getHorario(),
          aula.getCapacidade(),
          inscritos
          );
    }
    throw new Exception("Aula não encontrada");
  }

  public Aula criar(Aula novaAula){
    return this.aulaRepository.save(novaAula);

  }

  public AulaResponseDTO editar(Aula novaAula, Long id) throws Exception{
    Optional<Aula> aulaOpcional = aulaRepository.findById(id);
    if(aulaOpcional.isPresent()){
      Aula aulaPresente = aulaOpcional.get();
      aulaPresente.setNome(novaAula.getNome());
      aulaPresente.setInstrutor(novaAula.getInstrutor());
      aulaPresente.setHorario(novaAula.getHorario());
      aulaPresente.setCapacidade(novaAula.getCapacidade());
      int inscritos = inscricaoRepository.countByAulaId(id);
      this.aulaRepository.save(aulaPresente);
      return new AulaResponseDTO(
          aulaPresente.getId(),
          aulaPresente.getNome(),
          aulaPresente.getInstrutor(),
          aulaPresente.getHorario(),
          aulaPresente.getCapacidade(),
          inscritos
          );
    }
    throw new Exception("Aula não encontrado");
  }
  public void deletar(Long id) throws Exception{
    if (this.aulaRepository.existsById(id)){
      this.aulaRepository.deleteById(id);
      return;
    }
    throw new Exception("Aula não encontrada");
  }


}
