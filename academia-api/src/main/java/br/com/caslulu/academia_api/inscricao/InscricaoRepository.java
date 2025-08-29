package br.com.caslulu.academia_api.inscricao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long>{
  int countByAulaId(Long aulaId);

}
