import React, {useState, useEffect} from 'react';

function Login(){

  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  return (
  <div>
    <form className="flex flex-col items-center">
        <input type="text" name="email" id="email" value={email} onChange={(e)=> setEmail(e.target.value)}placeholder="seuemail@example.com" className="my-3"/>
        <input type="password" name="senha" id="senha" value={senha} onChange={(e)=> setSenha(e.target.value)} placeholder="Sua senha" className="my-3"/>
        <button type="submit" className="cursor-pointer mx-3 bg-blue-500 rounded-2xl" >Logar</button>
    </form>
  </div>
  );
}


export default Login;
