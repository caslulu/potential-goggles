package br.com.caslulu.academia_api.aula;

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
public class AulaController{
  private final AulaRepository repository;
  
  public AulaController(AulaRepository repository){
    this.repository = repository;
  }


  @GetMapping("/aulas")
  public List<Aula> verAulas(){
    return this.repository.findAll();
  }
  
  @GetMapping("/aulas/{id}")
  public Aula verAula(@PathVariable Long id){
  Optional<Aula> aulaOpcional = this.repository.findById(id);
  if(aulaOpcional.isPresent()){
    return aulaOpcional.get();
  }
  return new Aula();
  }

  @PostMapping("/aulas")
  public Aula criarAula(@RequestBody Aula novaAula){
    this.repository.save(novaAula);
    return novaAula;
  }

  @PutMapping("/aulas/{id}")
  public Aula modificarAula(@RequestBody Aula aulaCorrigida, @PathVariable Long id){
    Optional<Aula> aulaOpcional = this.repository.findById(id);
    if(aulaOpcional.isPresent()){
      Aula aulaEncontrada = aulaOpcional.get();
      aulaEncontrada.setNome(aulaCorrigida.getNome());
      aulaEncontrada.setHorario(aulaCorrigida.getHorario());
      aulaEncontrada.setInstrutor(aulaCorrigida.getInstrutor());
      aulaEncontrada.setCapacidade(aulaCorrigida.getCapacidade());
      this.repository.save(aulaEncontrada);
      return aulaEncontrada;
    }
    return new Aula();
  }

  @DeleteMapping("/aulas/{id}")
  public String deletarAula(@PathVariable Long id){
    try{
      this.repository.deleteById(id);
      return "Deletada Com Sucesso";
    }
    catch(Exception e){
      return "Não foi possivel deletar, veja se essa aula realmente existe";
    }
  }


}
