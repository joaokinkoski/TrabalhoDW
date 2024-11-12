import React, { useEffect, useState, useRef  } from 'react';
import '../assets/css/ModalEdit.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from "axios"
import { faCircleXmark } from '@fortawesome/free-regular-svg-icons';
import { faCircleCheck } from '@fortawesome/free-regular-svg-icons';
import '../assets/css/ModalDelete.css';
import ModalDeleteFoto from './ModalDeleteFoto'



const ModalEdit = ({ onClose, playerData }) => {


    const [localPlayerData, setLocalPlayerData] = useState(playerData);
    const [posicoes, setPosicoes] = useState();
    const [times, setTimes] = useState();
    const [imgUpada, setImgUpada] = useState('');
    const [file, setFile] = useState(null);
    const [modalDeleteFotoOpen, setModalDeleteFotoOpen] = useState(false);
    const fileInputRef = useRef(null);
    
    const openModalDeleteFoto = () => {
        setModalDeleteFotoOpen(true);
        const escondeModal = document.getElementById('fecharModalAnim');
        escondeModal.style.display = 'none';
      }
      const closeModalDeleteFoto = () => {
        setModalDeleteFotoOpen(false);
        const apareceModal = document.getElementById('fecharModalAnim');
        apareceModal.style.display = 'block';
      }

    const previewImagem = (event) => {
        const fileInput = event.target;
        const selectedFile = fileInput.files[0];

        if(selectedFile){
            if(selectedFile.type.startsWith('image')){
                const reader = new FileReader();
                reader.onload = (e) => {
                    setImgUpada(e.target.result);
                }
                reader.readAsDataURL(selectedFile);
                setFile(selectedFile);
                console.log(selectedFile);
            }else{
                setImgUpada('');
                alert('Selecione um arquivo de imagem')
            }
        }
    }

    const triggerInput = () => {
        // Dispara o clique no input de arquivo
        fileInputRef.current.click();
    };


    useEffect(() => {
    const getPosicoes = async () => {
        try{
            const result = await axios.get("http://localhost:8000/posicoes/listar");
            setPosicoes(result.data);
        }catch(error){
            console.error(error);
            setPosicoes("ERRO");
        }
    }
    const getTimes = async () => {
        try{
            const result = await axios.get("http://localhost:8000/times/listar");
            setTimes(result.data);
        }catch(error){
            console.error(error);
            setTimes("ERRO");
        }
    }
    getPosicoes();
    getTimes();
}, []);
useEffect(() => {
    if (localPlayerData && localPlayerData.caminhoImg) {
        // Define o caminho completo da imagem
        setImgUpada(`../fotos/${localPlayerData.caminhoImg}`);
    }
}, [localPlayerData]);

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setLocalPlayerData((prevData) => ({
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
    const handleImageSave = async () => {
        if(!file){
            alert("Anexe uma imagem");
            return;
        }

        const formData = new FormData();
        formData.append('image', file);
        formData.append('cod_jogador', localPlayerData.cod_jogador);


        try{
            const response = await axios.post('http://localhost:8000/fotos/salvar', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            console.log(response.data);
            alert("Dados salvos com sucesso!");
            location.reload();
        }catch (error){
            console.error(error);
            alert('Erro')
        }
    }

    const handleDataSave = async () => {
        const jogadorData = {
            nome: localPlayerData.nome,
            email: localPlayerData.email,
            posicao: {posicaoId: localPlayerData.posicao.posicaoId },
            times: {timeId: localPlayerData.times.timeId}
        };
        console.log(jogadorData)
        try{
            const response = await axios.put(`http://localhost:8000/jogador/update/${localPlayerData.cod_jogador}`, jogadorData);
            console.log(response.data);
            alert("Dados salvos!");
            location.reload();
        }catch(error){
            console.error(error);
            alert('Erro ao salvar dados');
        }
    }
    return (
        <div className="modal-overlay" onClick={handleOverlayClick}>
            <div className="modal-content" id='fecharModalAnim'>
                <div className='d-flex justify-content-between align-items-center'>
              <h2 className='mb-0' style={{fontWeight: '700'}}>Editar Jogador</h2>
              <button onClick={onClose} className='button-close'>
              <FontAwesomeIcon icon="x" style={{height: '2rem', width: '2rem'}}/>
              </button>
              </div>
              <hr />
                <div className='row'>
                    <div className="col-md-6">
                        <div className='d-flex h-75 align-items-start flex-wrap flex-column mt-5 justify-content-between'>
                            <div className=''>
                                <label htmlFor="" style={{fontWeight: '700'}}>Nome:</label>
                                <input value={localPlayerData.nome} onChange={handleInputChange} className="input" name="nome"/>
                            </div>
                            <div className=''>
                                <label htmlFor="" style={{fontWeight: '700'}}>Email:</label>
                                <input value={localPlayerData.email} onChange={handleInputChange} className="input" name="email"/>
                            </div>
                            <div className=''>
                                <label htmlFor="" style={{fontWeight: '700'}}>Posição:</label>
                                <select className='select' name="posicao" id="" value={localPlayerData.posicao.posicaoId}
                                onChange={(e) => {
                                    const selectedPosicaoId = e.target.value;
                                    setLocalPlayerData((prevData) => ({
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
                        <select name="times" value={localPlayerData.times.timeId} id="" className="select"
                        onChange={(e) => {
                            const selectedTimeId = e.target.value;
                            setLocalPlayerData((prevData) => ({
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
                    <div className="col-md-6">
                        <div className='primeiroCampo'>
                            <div className='campoImg'>
                                <img src={imgUpada} className='imageEdit' alt="" />
                            </div>
                        </div>
                        <br />
                        <input type="file" accept='image/*' className="d-none" ref={fileInputRef} onChange={previewImagem}/>
                        <div className='d-flex justify-content-between'>
                            <button onClick={triggerInput} className='button-anexo'>
                                <FontAwesomeIcon icon="fa-solid fa-arrow-up-from-bracket" className='svgArrow' />
                                Anexar foto
                            </button>
                            <button onClick={handleImageSave} disabled={!imgUpada} className='button-salvar'>
                                <FontAwesomeIcon icon="fa-solid fa-check" className='svgCheck' />
                                Salvar Imagem
                            </button>
                        </div>
                        <div className='d-flex justify-content-center'>
                            <button disabled={!imgUpada} onClick={openModalDeleteFoto} className='button-deletar'>
                                <FontAwesomeIcon icon="fa-solid fa-trash-can" className='svgTrash' />
                                Deletar
                            </button>
                        </div>
                    </div>
                    </div>
                </div>
                    {modalDeleteFotoOpen && (
                    <ModalDeleteFoto isOpen={modalDeleteFotoOpen} onClose={closeModalDeleteFoto} onSave={playerData} localPlayerData={playerData} />
                    )}
            </div>
            
    );
};

export default ModalEdit;
