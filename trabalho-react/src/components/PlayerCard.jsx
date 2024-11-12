import React, {useState, useEffect} from 'react';
import ModalEdit from './ModalEdit';
import ModalDelete from './ModalDelete';
import 'bootstrap/dist/css/bootstrap.min.css';

function PlayerCard({ item }) {

    const [modalOpen, setModalOpen] = useState(false);
    const [modalDeleteOpen, setModalDeleteOpen] = useState(false);
    const [imageSrc, setImageSrc] = useState('');

    const openModal = () => {
        setModalOpen(true);
    }
    const closeModal = () => {
        setModalOpen(false);
    }
    const openModalDelete = () => {
      setModalDeleteOpen(true);
    }
    const closeModalDelete = () => {
      setModalDeleteOpen(false);
    }

    useEffect(() => {
      const loadImage = async () => {
        try {
          const img = await import(`../assets/imgs/times/${item.times.timeId}.png`);
          setImageSrc(img.default);
        } catch (error) {
          console.error('Erro ao carregar a imagem:', error);
          setImageSrc('path/to/default/image.png');
        }
      };
      
  
      loadImage();
    }, [item.times.timeId]);

  return (
    <div className="card" style={{width: '22rem', marginTop: '2rem', marginLeft: '1rem', marginRight: '1rem'}}>
    { item.caminhoImg ? (
      <img className="card-img-top" src={`../fotos/${item.caminhoImg}`} alt="Card image cap" style={{height: '20rem', width: '17rem', border: '2px dashed #4DD21D', borderRadius: '2rem'}} />
    ) : (
      <div style={{height: '20rem', width: '17rem', display: 'flex', alignItems: 'center', justifyContent: 'center', backgroundColor: '#f0', borderBottom: '1px dashed grey'}}>
        <span style={{userSelect: 'none', color: '#6b7280', fontSize: '1.5rem', lineHeight: '1rem', inset: '0px'}}>
          Jogador sem foto
        </span>
      </div>
    )}
    <div className="card-body">
      <div className='d-flex justify-content-center position-relative'>
      <h5 className="card-title" style={{ marginRight: '0.5rem' }}>{item.nome}</h5>
      <img src={imageSrc} alt=""  style={{position: 'absolute', right: '1%', width: '50px', height: '50px', border: '1px dotted black', borderRadius: '50%'}} />
      </div>
      <h6 className="card-subtitle mb-1 text-muted">{item.posicao.nome}</h6>
      <div className="d-flex" style={{position: 'relative', top: '2rem'}}>
      <button onClick={openModal} className='button-edit' style={{marginRight: '3rem'}}>
        <span>
          Editar
        </span>
      </button>
      <button onClick={openModalDelete} className="button-edit">
        <span>
          Deletar
        </span>
      </button>
      </div>
    </div>
    {modalOpen && (
        <ModalEdit isOpen={modalOpen} onClose={closeModal} onSave={item} playerData={item}/>
    )}
    {modalDeleteOpen && (
        <ModalDelete isOpen={modalDeleteOpen} onClose={closeModalDelete} onSave={item} playerData={item} />
    )}
  </div>
  )
}

export default PlayerCard;
