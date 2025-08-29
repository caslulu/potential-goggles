package br.com.caslulu.academia_api.inscricao;


import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/inscricoes")
@CrossOrigin
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
}
