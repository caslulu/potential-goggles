
package br.com.caslulu.academia_api;

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
@CrossOrigin()
public class AlunoController {

  private final AlunoRepository repository;

  public AlunoController(AlunoRepository repository){
    this.repository = repository;
  }

  @GetMapping("/alunos")
  public List<Aluno> verAlunos(){
    return this.repository.findAll();
  }

  @PostMapping("/alunos")
  public Aluno adicionarAluno(@RequestBody Aluno alunoAdicionar){
    this.repository.save(alunoAdicionar);
    return alunoAdicionar;
  }

  @PutMapping("/alunos/{id}")
  public Aluno modificarAluno(@PathVariable Long id, @RequestBody Aluno alunoModificado){
  Optional<Aluno> alunoOpcional = this.repository.findById(id);
  if (alunoOpcional.isPresent()){
    Aluno alunoEncontrado = alunoOpcional.get();
    alunoEncontrado.setNome(alunoModificado.getNome());
    alunoEncontrado.setEmail(alunoModificado.getEmail());
    return alunoEncontrado;
  }
  else {
    return new Aluno("não encontrado", "404");
  }
  }
  @DeleteMapping("/alunos/{id}")
  public String deletarAluno(@PathVariable Long id){
    try{
      this.repository.deleteById(id);
      return "Deletado Com Sucesso.";

    }catch(Exception e){
      return "Não foi possivel encontrar.";
    }
  }
  

}
