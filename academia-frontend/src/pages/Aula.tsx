import React, {useEffect, useState} from 'react';
import {fetchAutenticado} from '../services/api.ts';
import {useAuth} from '../contexts/AuthContext.tsx';

function Aula() {

  const { user } = useAuth();
  const [aulas, setAulas] = useState([]);
  const [aulaParaEditar, setAulaParaEditar] = useState(null);
  const [novaAula, setNovaAula] = useState("");
  const [novoHorario, setNovoHorario] = useState("");
  const [novoInstrutor, setNovoInstrutor] = useState("");
  const [novaCapacidade, setNovaCapacidade] = useState("");


  const [minhasInscricoes, setMinhasIncricoes] = useState([]);

  useEffect (()=> {
    const buscarAulas = async () => {
      try{
        const data = await fetchAutenticado('/aulas');
        setAulas(data);
        if (user && user.alunoId){
        const dataInscricoes = await fetchAutenticado(`/inscricoes/aluno/${user.alunoId}`);
        setMinhasIncricoes(dataInscricoes);
        }
      } catch (error){
        console.log("Erro ao buscar aulas", error);
      }
    };
    buscarAulas();
  }, [user]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const postData = { nome: novaAula,
      horario: novoHorario,
      instrutor: novoInstrutor,
      capacidade: novaCapacidade
    };
    try{
      if(aulaParaEditar == null){
        const novaAulaSalva = await fetchAutenticado('/aulas', {
          method: 'POST',
          body: JSON.stringify(postData)
          });


        setAulas(aulaAntiga => [...aulaAntiga, novaAulaSalva]);

      }
      else{
        const response = await fetchAutenticado(`/aulas/${aulaParaEditar.id}`, {
          method: 'PUT',
          body: JSON.stringify(postData)
          });
        const aulaEditada = await response.json();
        setAulas(listaAntiga => listaAntiga.map(aula=> aula.id === aulaEditada.id ? aulaEditada : aula
        ));
      }
      setNovaAula("");
      setNovoHorario("");
      setNovoInstrutor("");
      setNovaCapacidade("");
      setAulaParaEditar(null);
    }
    catch(error){
      return "nao foi possivel criar a aula";
    }
  };

  const handleDelete = async (id: Long) => {
    try{
      await fetchAutenticado(`/aulas/${id}`, {method: "DELETE"});
      setAulas(aulasAtuais => aulasAtuais.filter(aula => aula.id !== id));
      alert("aula deletada com sucesso");
    } catch ( error){
      console.error("Falha ao deletar aula: ", error);
      alert(`nao foi possivel deletar a aula: ${error.message}`);
    }
  }
  const handleEdit = (aula) =>{
      setAulaParaEditar(aula);
      setNovaAula(aula.nome);
      setNovoHorario(aula.horario);
      setNovoInstrutor(aula.instrutor);
      setNovaCapacidade(aula.capacidade);
    }

  const handleInscrever = async (aulaId)=>{
    const postData = {
      aulaId: aulaId,
      alunoId: user.alunoId
    };
    const inscricaoNova = await fetchAutenticado('/inscricoes', {
      method:"POST",
      body: JSON.stringify(postData)
    });
    alert("Inscricao realizada com sucesso");
      setMinhasIncricoes(inscricaoAntiga => [...inscricaoAntiga, inscricaoNova]);
    setAulas(listaAntiga => listaAntiga.map(aula => {
      if (aula.id === aulaId){
        return {...aula, inscritos: (aula.inscritos ?? 0)+1};
      } else{
        return aula;
      }
    })
    );
  }

  const handleCancelarInscricao = async (inscricaoId: Long, aulaId: Long) => {
    const inscricaoCancelada = await fetchAutenticado(`/inscricoes/${inscricaoId}`, {method: "DELETE"});
    setMinhasIncricoes(inscricoesAtuais => inscricoesAtuais.filter(inscricao=> inscricao.id !== inscricaoId));
    setAulas(aulasAtuais => aulasAtuais.map(aula => 
    aula.id === aulaId ? {... aula, inscritos: aula.inscritos - 1} : aula
    ));

  }
  return(
     <div>
      <hr/>

        { user && user.role === "ADMIN" && (
        <form onSubmit={handleSubmit}>
          <label htmlFor="aula">Nome:</label>
          <input type="text" id="aula" name="aula" value={novaAula} onChange={(e) => setNovaAula(e.target.value)}placeholder="Nome da aula..."/>

          <label htmlFor="horario">Horario:</label>
          <input type="text" id="horario" name="horario" value={novoHorario} onChange={(e) => setNovoHorario(e.target.value)} placeholder="Horario da aula..."/>

          <label htmlFor="instrutor">Instrutor:</label>
          <input type="text" id="instrutor" name="instrutor" value={novoInstrutor} onChange={(e) => setNovoInstrutor(e.target.value)} placeholder="Nome do instrutor..."/>

          <label htmlFor="capacidade">Capacidade:</label>
            <input type="text" id="capacidade" name="capacidade" value={novaCapacidade} onChange={(e) => setNovaCapacidade(e.target.value)} placeholder="Capacidade da aula..."/>

          <button type="submit" className="cursor-pointer my-3 bg-blue-500 rounded-2xl">Criar Aula</button>
        </form>
        )}

        <hr/>

      <ul>
        {aulas.map(aula =>{
          const inscricaoAluno = minhasInscricoes.find(inscricao => inscricao.aula.id === aula.id)
          return (
        <li key={aula.id}> Tipo de aula: {aula.nome} -
            Horario da aula: {aula.horario} - 
            Instrutor da aula: {aula.instrutor} -
            Capacidade: {aula.inscritos ?? 0}/{aula.capacidade} Pessoas
            { user && user.role === 'ALUNO' && (
              inscricaoAluno
                ? <button className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" onClick={()=> handleCancelarInscricao(inscricaoAluno.id, aula.id)}> Cancelar</button>
                : <button className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" onClick={()=> handleInscrever(aula.id)}> Inscrever</button>
              )}
            { user && user.role === 'ADMIN' && (
              <>
                <button className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" onClick={ ()=> handleDelete(aula.id)}>deletar</button>
                <button className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" onClick={ ()=> handleEdit(aula)}>Editar</button>
              </>
            )}
          <hr/>
        </li>
        ); 
        })}
      </ul>

    </div>
  );
}

export default Aula;
