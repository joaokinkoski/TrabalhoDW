import React, { useEffect, useState, useRef  } from 'react';
import '../assets/css/ModalEdit.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from "axios"



const ModalEdit = ({onClose}) => {


    const [playerData, setPlayerData] = useState({
        nome: '',
        email: '',
        posicao: {posicaoId: 1},
        time: {timeId: 1}
    });
    const [posicoes, setPosicoes] = useState([]);
    const [times, setTimes] = useState([]);
    
    const triggerInput = () => {
        // Dispara o clique no input de arquivo
        fileInputRef.current.click();
    };


    useEffect(() => {
    const getPosicoes = async () => {
        try{
            const posicoesResponse = await axios.get("http://localhost:8000/posicoes/listar");
            setPosicoes(posicoesResponse.data);
        }catch(error){
            console.error(error);
            setPosicoes("ERRO");
        }
    }
    const getTimes = async () => {
        try{
            const timesResponse = await axios.get("http://localhost:8000/times/listar");
            setTimes(timesResponse.data);
        }catch(error){
            console.error(error);
            setTimes("ERRO");
        }
    }
    getPosicoes();
    getTimes();
}, []);

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setPlayerData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    }

    const handleOverlayClick = (event) => {
        if (event.target === event.currentTarget) {
            const fecharAnim = document.getElementById('fecharModalAnim')
            fecharAnim.classList.add('fecharAnimacao');
            setTimeout(() => {
            onClose();
            }, 400);
        }
    };
    const handleDataSave = async () => {
        const jogadorData = {
            nome: playerData.nome,
            email: playerData.email,
            posicao: {posicaoId: playerData.posicao.posicaoId || 1},
            times: {timeId: playerData.times?.timeId || 1}
        };
        try{
            console.log(jogadorData);
            const response = await axios.post(`http://localhost:8000/jogador/create`, jogadorData);
            console.log(response.data);
            alert("Jogador cadastrado!");
            location.reload();
        }catch(error){
            console.error(error);
            alert('Erro ao cadastrar jogador');
        }
    }
    return (
        <div className="modal-overlay" onClick={handleOverlayClick}>
            <div className="modal-content" style={{height: '60vh', width: '30vw'}} id='fecharModalAnim'>
                <div className='d-flex justify-content-between align-items-center'>
              <h2 className='mb-0' style={{fontWeight: '700'}}>Cadastrar jogador</h2>
              <button onClick={onClose} className='button-close'>
              <FontAwesomeIcon icon="x" style={{height: '2rem', width: '2rem'}}/>
              </button>
              </div>
              <hr />
                        <div className='d-flex h-75 align-items-center flex-wrap flex-column mt-2 justify-content-between'>
                            <div className=''>
                                <label htmlFor="" style={{fontWeight: '700'}}>Nome:</label>
                                <input value={playerData.nome} onChange={handleInputChange} className="input" name="nome"/>
                            </div>
                            <div className=''>
                                <label htmlFor="" style={{fontWeight: '700'}}>Email:</label>
                                <input value={playerData.email} onChange={handleInputChange} className="input" name="email"/>
                            </div>
                            <div className=''>
                                <label htmlFor="" style={{fontWeight: '700'}}>Posição:</label>
                                <select className='select' name="posicao" id="" value={playerData.posicao?.posicaoId || 1}
                                onChange={(e) => {
                                    const selectedPosicaoId = e.target.value;
                                    setPlayerData((prevData) => ({
                                        ...prevData,
                                        posicao: {posicaoId: selectedPosicaoId}
                                    }));
                                }
                                }>
                                {posicoes && posicoes.length > 0 ? (
                                posicoes.map((item,index) => (
                                    <option className='options' key={index} value={item.posicaoId}>{item.nome}</option>
                                    ))
                                ) : (
                                    <option value="">Erro</option>
                                )
                        }
                                </select>
                            </div>
                        <div>
                        <label htmlFor="" style={{fontWeight: '700'}}>Time:</label>
                        <select name="times" value={playerData.times?.timeId || 1} id="" className="select"
                        onChange={(e) => {
                            const selectedTimeId = e.target.value;
                            setPlayerData((prevData) => ({
                                ...prevData,
                                times: {timeId: selectedTimeId}
                            }));
                        }}
                        >
                            {times && times.length > 0 ? (
                                times.map((item,index) => (
                                    <option className='options' key={index} value={item.timeId}>{item.timeNome}</option>
                                ))
                            ) : (
                                <option value="">Erro</option>
                            )}
                        </select>
                        </div>
                        <div>
                            <button onClick={handleDataSave} className='button-salvar'>
                            <FontAwesomeIcon icon="fa-solid fa-check" className='svgCheck' />
                            Salvar dados
                            </button>
                        </div>
                        </div>
                    </div>
                    </div>
            
    );
};

export default ModalEdit;
