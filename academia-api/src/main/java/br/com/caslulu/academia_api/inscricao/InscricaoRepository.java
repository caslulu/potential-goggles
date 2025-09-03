package br.com.caslulu.academia_api.inscricao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long>{
  int countByAulaId(Long aulaId);
  List<Inscricao> findByAlunoId(Long alunoId);
  List<Inscricao> findByAulaId(Long aulaId);

}
