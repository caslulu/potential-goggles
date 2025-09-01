package br.com.caslulu.academia_api.inscricao;

import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/inscricoes")
public class InscricaoController{
  private final InscricaoService inscricaoService;
  public InscricaoController(InscricaoService inscricaoService)
  {
    this.inscricaoService = inscricaoService;
  }

  public record InscricaoRequestDTO(Long alunoId, Long aulaId){
  }
  
  @PostMapping
  public Inscricao inscreverAluno(@RequestBody InscricaoRequestDTO request){
    return inscricaoService.realizarInscricao(request.alunoId(), request.aulaId);
  }

  @GetMapping("/aluno/{id}")
  public List<Inscricao> buscarInscricoesPorAluno(@PathVariable Long id){
    return inscricaoService.verInscricoesAluno(id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removerInscricao(@PathVariable Long id) throws Exception{
    this.inscricaoService.cancelarInscricao(id);
    return ResponseEntity.noContent().build();
  }
}
