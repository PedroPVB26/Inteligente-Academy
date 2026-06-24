import React from 'react';
import { Link } from 'react-router-dom';
import './CourseCard.css';
import tecnologiaImage from '../../assets/technology_example.webp';

function CourseCard({ data = {} }) {
  const navigate = useNavigate(); // 2. Inicializar o navegador

  const {
    id, // Precisamos capturar o id que vem do banco de dados
    name = 'Curso sem título',
    description = 'Descrição não disponível',
    durationInSeconds = 0,
    publicationStatus = 'RASCUNHO',
    moduleCount = 0,
    lessonsCount = 0,
    tags = []
  } = data;
  const courseId = data.id;

  const hours = durationInSeconds ? `${Math.round(durationInSeconds / 3600)}h` : 'N/A';
  const category = tags[0]?.name ?? 'Sem categoria';
  const statusLabel = publicationStatus?.toString() ?? 'INDISPONÍVEL';

  // 3. Função que gerencia o clique
  const handleNavigateToCourse = () => {
    if (id) {
      navigate(`/courses/${id}`);
    } else {
      console.warn("Não foi possível redirecionar: ID do curso ausente.");
    }
  };

  return (
    // Adicionado onClick no card inteiro para melhorar a experiência
    <div className="course-card-style" onClick={handleNavigateToCourse} style={{ cursor: 'pointer' }}>
      <div className="card-image-container">
        <img 
          src={tecnologiaImage} 
          alt={name} 
          className="card-image" 
        />
        <div className="tags-left">
          <span className="tag-badge badge-green">{statusLabel}</span>
          <span className="tag-badge badge-blue">{category}</span>
        </div>
        <div className="tag-right">
          <span className="tag-badge badge-time">
            <span className="clock-icon">🕒</span> {hours}
          </span>
        </div>
      </div>

      <div className="card-info">
        <h3 className="course-title">{name}</h3>
        <p className="course-instructor">{description}</p>

        <div className="card-footer">
          <div className="footer-badges">
            <div className="badge-rating">
              {moduleCount} módulo(s)
            </div>
            <div className="badge-requirement">
              {lessonsCount} aula(s)
            </div>
          </div>
          {/* O botão também dispara a navegação */}
          <button className="btn-enroll" onClick={(e) => {
            e.stopPropagation(); // Evita disparar o onClick do card pai duas vezes
            handleNavigateToCourse();
          }}>
            Inscreva-se
          </button>
        </div>
      </div>
    </div>
  );
}

export default CourseCard;