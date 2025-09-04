package br.com.caslulu.academia_api.aluno;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AlunoController {

  private final AlunoService service;

  public AlunoController(AlunoService service){
    this.service = service;
  }

  @GetMapping("/alunos")
  public List<AlunoResponseDTO> verAlunos(){
    return service.verTodos();
  }

  @GetMapping("/alunos/{id}")
  public AlunoResponseDTO verAluno(@PathVariable Long id){
    return service.buscarPorId(id);
  }

  @PostMapping("/alunos")
  public Aluno adicionarAluno(@RequestBody Aluno alunoAdicionar) throws Exception{
    return service.criar(alunoAdicionar);
  }

  @PutMapping("/alunos/{id}")
  public AlunoResponseDTO modificarAluno(@PathVariable Long id, @RequestBody Aluno alunoModificado){
    return service.atualizar(id, alunoModificado);
  }
  @DeleteMapping("/alunos/{id}")
  public ResponseEntity<Void> deletarAluno(@PathVariable Long id){
    service.deletar(id);
    return ResponseEntity.noContent().build();
  }
  

}
