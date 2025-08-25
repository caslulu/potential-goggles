import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import MeuHeader from './components/MeuHeader.tsx';
import MeuFooter from './components/MeuFooter.tsx'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <MeuHeader/>
      <MeuFooter/>
    </>
  )
}

export default App
