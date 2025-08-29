import React, {useEffect, useState} from 'react';

function Aula() {
  const [aulas, setAulas] = useState([]);
  const [aulaParaEditar, setAulaParaEditar] = useState(null);
  const [novaAula, setNovaAula] = useState("");
  const [novoHorario, setNovoHorario] = useState("");
  const [novoInstrutor, setNovoInstrutor] = useState("");
  const [novaCapacidade, setNovaCapacidade] = useState("");
  useEffect (()=> {
    fetch("http://localhost:8080/aulas")
    .then(response => response.json())
    .then(data => setAulas(data));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const postData = {
      nome: novaAula,
      horario: novoHorario,
      instrutor: novoInstrutor,
      capacidade: novaCapacidade
    };
    try{
      if(aulaParaEditar == null){
        const response = await fetch('http://localhost:8080/aulas', {
          method: 'POST',
          headers: {'Content-Type': 'application/json' },
          body: JSON.stringify(postData)
          });

        if(!response.ok){
          throw new Error("nao foi possivel criar");
        }

        const novaAulaSalva = await response.json();
        setAulas(aulaAntiga => [...aulaAntiga, novaAulaSalva]);

      }
      else{
        const response = await fetch(`http://localhost:8080/aulas/${aulaParaEditar.id}`, {
          method: 'PUT',
          headers: {'Content-Type': 'application/json' },
          body: JSON.stringify(postData)
          });
        if(!response.ok){
          throw new Error("nao foi possivel editar");
        }
        const aulaEditada = await response.json();
        setAulas(listaAntiga => listaAntiga.map(aula=> aula.id === aulaEditada.id ? aulaEditada : aula
        ));
      }
      setNovaAula("");
      setNovoHorario("");
      setNovoInstrutor("");
      setNovaCapacidade("");
    }
    catch(error){
      return "nao foi possivel criar a aula";
    }
  };

  function handleDelete(id: Long){
  fetch(`http://localhost:8080/aulas/${id}`, {method: 'DELETE'})
    .then(response => {
        if(response.ok){
        setAulas(aulas.filter(a=> a.id !== id));
        }
        else{
          alert("não foi possivel deletar essa aula.");
        }
      });
  }
  const handleEdit = (aula) =>{
      setAulaParaEditar(aula);
      setNovaAula(aula.nome);
      setNovoHorario(aula.horario);
      setNovoInstrutor(aula.instrutor);
      setNovaCapacidade(aula.capacidade);
    }
  return(
     <div>
      <hr/>
      <ul>
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
        <hr/>

        {aulas.map(aula =>(
        <li key={aula.id}> Tipo de aula: {aula.nome} -
            Horario da aula: {aula.horario} - 
            Instrutor da aula: {aula.instrutor} -
            Capacidade: {aula.capacidade} Pessoas
            <button className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" onClick={ ()=> handleDelete(aula.id)}>deletar</button>
            <button className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" onClick={ ()=> handleEdit(aula)}>Editar</button>
          <hr/>
        </li>
        ))}
      </ul>
    </div>
  );
}

export default Aula;
