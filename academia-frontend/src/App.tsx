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
function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <nav>
        <Link to="/">Pagina Inicial</Link> |{" "}
        <Link to="/alunos">Gerenciar Alunos</Link> |{" "}
        <Link to="/aulas">Gerenciar Aulas</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/alunos" element={<Aluno/>}/>
        <Route path="/aulas" element={<Aula/>}/>
      </Routes>

    </>
    
  )
}

export default App
