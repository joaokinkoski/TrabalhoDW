import { useState, useEffect } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import axios from "axios"
import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import PlayerCard from './components/PlayerCard';
import Header from './components/Header';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';

library.add(fas);

function App() {
  const [response, setResponse] = useState();

    useEffect(() => {
    const getPlayers = async () => {
      try{
        const result = await axios.get("http://localhost:8000/jogador/listar");
        console.log(result);
        if(result.data.length > 0){
          const sortedP = result.data.sort((a, b) => a.cod_jogador - b.cod_jogador);
          setResponse(sortedP);
        }
      } catch(error){
        console.error("Erro axios", error);
        setResponse("Erro na requisição");
      } 
    };
    getPlayers();
  }, []);
  
  return (
    <>
     <Header />
     <div className='mt-4'>
    <div className="d-flex justify-content-around flex-wrap">
    {Array.isArray(response) && response.length > 0 ? (
      response.map((item,index) => (
        <PlayerCard key={index} item={item} />
      ))
      ) : (
        <h3 style={{fontWeight: '900', lineHeight: '1.5rem', fontSize:'3rem'}}>Nenhum jogador cadastrado</h3>
    )}
   </div>
   </div>
  </>
  )
}

export default App
