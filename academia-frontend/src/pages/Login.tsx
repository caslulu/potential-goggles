import React, {useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import {jwtDecode } from 'jwt-decode';
import { useAuth } from '../contexts/AuthContext.tsx';

function Login(){
  
  const { login } = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try{
      const response = await fetch("http://localhost:8080/auth/login", {
        method: 'POST',
        headers: {'Content-Type': 'application/json' },
        body: JSON.stringify({email,senha})
      });

      if (!response.ok){
        alert("erro");
        throw new Error("Não foi possivel logar");
      }

      const data = await response.json();

      login(data.token);

      alert("login bem sucedido!");
      navigate('/alunos');

    }catch (error){
      return "erro ao logar";
    }
  }
  return (
  <div>
    <form onSubmit={handleSubmit} className="flex flex-col items-center">
        <input type="text" name="email" id="email" value={email} onChange={(e)=> setEmail(e.target.value)}placeholder="seuemail@example.com" className="my-3"/>
        <input type="password" name="senha" id="senha" value={senha} onChange={(e)=> setSenha(e.target.value)} placeholder="Sua senha" className="my-3"/>
        <button type="submit" className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" >Logar</button>
    </form>
  </div>
  );
}


export default Login;
