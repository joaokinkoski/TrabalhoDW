import React, {useState} from "react";
import '../assets/css/Header.css'
import logo from '../assets/imgs/Logo.png'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import ModalCreate from './ModalCreate';


const Header = () => {
    const [modalCreateOpen, setModalCreateOpen] = useState(false);
    
    const openModalCreate = () => {
        setModalCreateOpen(true);
    }
    const closeModalCreate = () => {
        setModalCreateOpen(false);
    }

    return(
        <>
        <header className="header">
            <img src={logo} className="header-image"/>
            <button onClick={openModalCreate} className="Btn">
                <div className="sign">+</div>
                <div className="text">Cadastrar</div>
            </button>
        </header>
        {modalCreateOpen &&
    <ModalCreate isOpen={modalCreateOpen} onClose={closeModalCreate}/>
        }
    </>
    )
}

export default Header;