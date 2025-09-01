package br.com.caslulu.academia_api.aula;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;


@RestController
public class AulaController{
  private final AulaRepository repository;
  private final AulaService service;

  public AulaController(AulaRepository repository, AulaService service){
    this.repository = repository;
    this.service = service;
  }


  @GetMapping("/aulas")
  public List<AulaResponseDTO> verAulas(){
    return service.listarAulasComVagas();
  }
  
  @GetMapping("/aulas/{id}")
  public AulaResponseDTO verAula(@PathVariable Long id) throws Exception{
    return this.service.buscarPorId(id);
  }

  @PostMapping("/aulas")
  public Aula criarAula(@RequestBody Aula novaAula){
    return this.service.criar(novaAula);
  }

  @PutMapping("/aulas/{id}")
  public AulaResponseDTO modificarAula(@RequestBody Aula aulaCorrigida, @PathVariable Long id) throws Exception{
    return this.service.editar(aulaCorrigida, id);
  }

  @DeleteMapping("/aulas/{id}")
  public ResponseEntity<Void> deletarAula(@PathVariable Long id) throws Exception{
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }


}
