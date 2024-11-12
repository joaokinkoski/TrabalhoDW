import React, {useState, useEffect} from 'react';
import '../assets/css/ModalDelete.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleXmark } from '@fortawesome/free-regular-svg-icons';
import { faCircleCheck } from '@fortawesome/free-regular-svg-icons';

import axios from "axios"



function ModalDeleteFoto({ localPlayerData, onClose }) {

const [msg, setMsg] = useState('Deseja excluir foto?');
const [controlaIcon, setControlaIcon] = useState(true);

    const handleOverlayClick = (event) => {
        if (event.target === event.currentTarget) {
            const fecharAnim = document.getElementById('fecharModalDelAnim')
            fecharAnim.classList.add('fecharAnimacao');
            setTimeout(() => {
                onClose();
            }, 400);
        }
    }
    const animClose = () =>{
        const fecharAnim = document.getElementById('fecharModalDelAnim');
        fecharAnim.classList.add('fecharAnimacao');
        setTimeout(() => {
            onClose();
        }, 400);
    }
    const deleteImg = async () => {
        try{
            const response = await axios.delete('http://localhost:8000/fotos/deletar',{
               params: {cod_jogador: localPlayerData.cod_jogador}
            });
            if(response.status === 200){
                const excluiAnim = document.getElementById('excluiDelAnim');
                excluiAnim.classList.add('excluiJogador');
                setTimeout(() => {
                  setMsg("Foto excluida!");
                  setControlaIcon(false);  
                }, 600);
                setTimeout(() => {
                    location.reload();
                }, 1500);
            }
        }catch(error){
            console.error('Erro ao deletar', error);
        }
    }

    return(
        <>
            <div className='fundoModal' onClick={handleOverlayClick}>
                <div className='meioModal' id='fecharModalDelAnim'>
                    <div className='bodyModal'>
                        <div style={{padding: '1rem', textAlign: 'center'}}>
                            { controlaIcon ? (
                                <FontAwesomeIcon icon={faCircleXmark} size="7x" color='red' />
                            ):(
                                <FontAwesomeIcon icon={faCircleCheck} size='7x' color='green' />
                            )}
                            <button onClick={animClose} className='button-close' style={{position:'absolute', top:'1rem', right: '1rem'}}>
                                 <FontAwesomeIcon icon="x" style={{height: '2rem', width: '2rem'}}/>
                            </button>
                        </div>
                        <h3 style={{marginBottom: '1.5rem', fontSize: '1.25rem', lineHeight: '1.75rem', fontWeight: '600', color: 'black'}} id='excluiDelAnim'>{msg}</h3>
                        <div className='d-flex justify-content-around'> 
                            <button onClick={deleteImg} className='button-edit' id='botaoExcluir'>
                                Excluir
                            </button>
                            <button className='button-edit' onClick={animClose}>
                                Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
export default ModalDeleteFoto;