import React, {useEffect, useState} from 'react';
import { fetchAutenticado } from '../services/api.ts';

function Aluno() {
  const [alunos, setAlunos] = useState([]);
  const [novoAluno, setNovoAluno] = useState('');
  const [novoEmail, setNovoEmail] = useState('');
  const [novaSenha, setNovaSenha] = useState('');

  const [alunoParaEditar, setAlunoParaEditar] = useState(null);
  useEffect(() => {
    const buscarAlunos = async () => {
      try{
        const data = await fetchAutenticado('/alunos');
        setAlunos(data);
      } catch (error) {
        console.error("Erro ao buscar alunos", error);
      }
    };
    buscarAlunos();
  },[]);

  const handleDelete = async (id: Long) =>{
    try{
      await fetchAutenticado(`/alunos/${id}`, {method: 'DELETE'});
      setAlunos(alunosAtuais => alunosAtuais.filter(aluno => aluno.id !== id));
      alert("Aluno deletado com sucesso");
    } catch (error){
      console.error("Falha ao deletar aluno:", error);
      alert(`Não foi possivel deletar o aluno: ${error.message}`);
    }
  }
  const handleSubmit = async(e)=>{
      e.preventDefault();
      const postData = {
        nome:novoAluno,
        email:novoEmail,
        senha:novaSenha
      };
    try {
      if (alunoParaEditar == null){
        const novoAluno = await fetchAutenticado('/alunos', {
          method: 'POST',
          body: JSON.stringify(postData)
          });

        setAlunos(listaAntiga => [...listaAntiga, novoAluno]);
      }
    else{
        const alunoAtualizado = await fetchAutenticado(`/alunos/${alunoParaEditar.id}`, {
          method: 'PUT',
          body: JSON.stringify(postData)
          });

        setAlunos(listaAntiga => listaAntiga.map(aluno=> aluno.id === alunoAtualizado.id ? alunoAtualizado : aluno
          ));
      }

      setNovoEmail('');
      setNovoAluno('');
      setNovaSenha('');
      setAlunoParaEditar(null);

    } catch (error) {
        return "erro ao criar pessoa";
    }

  }

  const handleEditClick = (aluno) =>{
  setAlunoParaEditar(aluno);
  setNovoAluno(aluno.nome);
  setNovoEmail(aluno.email);

  }
  return(
    <div>  
      <h1>Gerenciamento de Alunos</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="nome">Nome: </label>
          <input type="text" id="nome" name="nome" value={novoAluno} onChange={(e) => setNovoAluno(e.target.value)} placeholder="Digite o seu nome..."/>
        <label htmlFor="email">Email: </label>
          <input type="text" id="email" name="email" value={novoEmail} onChange={(e) => setNovoEmail(e.target.value)} placeholder="Digite o seu nome..."/>
        <label htmlFor="senha">Senha: </label>
          <input type="password" id="senha" name="senha" value={novaSenha} onChange={(e) => setNovaSenha(e.target.value)} placeholder="Digite a sua senha.."/>
        <button type="submit" className="cursor-pointer mx-3 bg-blue-500 rounded-2xl">Enviar</button>
      </form>
      <ul>
        {alunos.map(aluno => (
          <li key={aluno.id}>{aluno.nome} - {aluno.email} - 
          <button className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" onClick={()=> handleDelete(aluno.id)}>Deletar</button>
          <button className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" onClick={()=> handleEditClick(aluno)}>Editar</button>
        </li>
        ))}
      </ul>
    </div>

  );
}

export default Aluno;
