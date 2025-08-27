import React, {useEffect, useState} from 'react';

function Aluno() {
  const [alunos, setAlunos] = useState([]);
  const [novoAluno, setNovoAluno] = useState('');
  const [novoEmail, setNovoEmail] = useState('');
  const [alunoParaEditar, setAlunoParaEditar] = useState(null);
  useEffect(() => {
   fetch('http://localhost:8080/alunos')
    .then(response => response.json())
    .then(data => setAlunos(data))
  },[]);

  function handleDelete(id: Long){
    fetch(`http://localhost:8080/alunos/${id}`, {method: 'DELETE'})
    .then(response => {
        if (response.ok){
          setAlunos(alunos.filter(a => a.id !== id));
        }
        else{
          alert("Erro ao deletar o aluno");
        }
      });

  }
  const handleSubmit = async(e)=>{
      e.preventDefault();
      const postData = {
        nome:novoAluno,
        email:novoEmail
      };
    try {
      if (alunoParaEditar == null){
        const response = await fetch('http://localhost:8080/alunos', {
          method: 'POST',
          headers: {'Content-Type': 'application/json' },
          body: JSON.stringify(postData)
          });


        if (!response.ok){
          throw new Error("erro");
        }

        const novoAlunoSalvo = await response.json();
        setAlunos(listaAntiga => [...listaAntiga, novoAlunoSalvo]);
      }
    else{
        const response = await fetch(`http://localhost:8080/alunos/${alunoParaEditar.id}`, {
          method: 'PUT',
          headers: {'Content-Type': 'application/json' },
          body: JSON.stringify(postData)
          });


        if (!response.ok){
          throw new Error("erro");
        }
        const alunoAtualizado = await response.json();
        setAlunos(listaAntiga => listaAntiga.map(aluno=> aluno.id === alunoAtualizado.id ? alunoAtualizado : aluno
          ));
      }

      setNovoEmail('');
      setNovoAluno('');

    } catch (error) {
        return "erro ao criar pessoa";
    }

  };

  const handleEditClick = (aluno) =>{
  setAlunoParaEditar(aluno);
  setNovoAluno(aluno.nome);
  setNovoEmail(aluno.email);

  };
  return(
    <div>  
      <h1>Gerenciamento de Alunos</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="nome">Nome: </label>
          <input type="text" id="nome" name="nome" value={novoAluno} onChange={(e) => setNovoAluno(e.target.value)} placeholder="Digite o seu nome..."/>
        <label htmlFor="email">Email: </label>
          <input type="text" id="email" name="email" value={novoEmail} onChange={(e) => setNovoEmail(e.target.value)} placeholder="Digite o seu nome..."/>
        <button type="submit" className="cursor-pointer">Enviar</button>
      </form>
      <ul>
        {alunos.map(aluno => (
          <li key={aluno.id}>{aluno.nome} - {aluno.email} - 
          <button className="cursor-pointer" onClick={()=> handleDelete(aluno.id)}>Deletar</button>
          <button className="cursor-pointer" onClick={()=> handleEditClick(aluno)}>Editar</button>
        </li>
        ))}
      </ul>
    </div>

  );
}

export default Aluno;
