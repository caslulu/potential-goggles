package br.com.caslulu.academia_api.aula;


public record AulaResponseDTO(
  Long id,
  String nome,
  String instrutor,
  String horario,
  Integer capacidade,
  int inscritos
){}

