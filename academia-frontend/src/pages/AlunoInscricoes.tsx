import { useAuth } from '../contexts/AuthContext.tsx';
import { fetchAutenticado } from '../services/api.ts';

import React, { useEffect, useState} from 'react';

function AlunoInscricoes() {
  const { user } = useAuth();
  const [inscricoes, setInscricoes] = useState([]);

  useEffect (() => {
    const buscarInscricoes = async () => {
      try{
        const data = await fetchAutenticado(`/inscricoes/aluno/${user.alunoId}`);
        setInscricoes(data);
        } catch (error) {
        console.log("Erro ao buscar inscricoes: ", error);
      }
  };
    buscarInscricoes();
  }, [user]);

  return (
  <>
    <ul>
      {inscricoes.map(inscricao => (
      <li key={inscricao.id}>
        Nome da aula: {inscricao.aula.nome} { }
        Horario da aula: {inscricao.aula.horario} { }
        Instrutor da aula: {inscricao.aula.instrutor} { } 
        Capacidade: {inscricao.aula.inscricoes ?? 0}/ {inscricao.aula.capacidade}
        <hr/>
      </li>
      ))}
    </ul>
  </>
  );

}

export default AlunoInscricoes;
