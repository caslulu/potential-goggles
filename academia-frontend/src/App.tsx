import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import {Routes, Route, Link} from 'react-router-dom'

import MeuHeader from './components/MeuHeader.tsx'
import MeuFooter from './components/MeuFooter.tsx'

import Home from './pages/Home.tsx'
import Aluno from './pages/Aluno.tsx'
import Aula from './pages/Aula.tsx'
import Login from './pages/Login.tsx'
import AlunoInscricoes from './pages/AlunoInscricoes.tsx';

import { useAuth } from './contexts/AuthContext.tsx';

function App() {
  const {user} = useAuth();
  return (
    <>
      <nav>
        <Link to="/">Pagina Inicial</Link> |{" "}
        <Link to="/alunos">Gerenciar Alunos</Link> |{" "}
        <Link to="/aulas">Gerenciar Aulas</Link> | {" "}
        { user && user.role === "ALUNO" && <Link to="/minhasinscricoes/">Ver Minhas Inscricoes |</Link>}

        <Link to="/login">Logar</Link>
      </nav>
      <hr/>

      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/alunos" element={<Aluno/>}/>
        <Route path="/aulas" element={<Aula/>}/>
        <Route path="/minhasinscricoes" element={<AlunoInscricoes/>}/>
        <Route path="/login" element={<Login/>}/>
      </Routes>

    </>
    
  )
}

export default App
