import React from 'react';
import './CourseCard.css';
import tecnologiaImage from '../../assets/tecnologia.png';

function CourseCard({ data = {} }) {
  const {
    title = 'Introdução a IA',
    code = 'IA01',
    instructor = 'Robson Parmesan Bonidia',
    rating = '5.0',
    req = 'PY01',
    hours = '40 horas',
    level = 'Nível',
    category = 'Categoria'
  } = data;

  return (
    <div className="course-card-style">
      {/* Parte Superior: Imagem e Tags Flutuantes */}
      <div className="card-image-container">
        <img 
          src={tecnologiaImage} 
          alt={title} 
          className="card-image" 
        />
        
        {/* Tags que ficam por cima da imagem */}
        <div className="tags-left">
          <span className="tag-badge badge-green">{level}</span>
          <span className="tag-badge badge-blue">{category}</span>
        </div>
        
        <div className="tag-right">
          <span className="tag-badge badge-time">
            <span className="clock-icon">🕒</span> {hours}
          </span>
        </div>
      </div>

      {/* Parte Inferior: Informações do Curso */}
      <div className="card-info">
        <h3 className="course-title">{title}</h3>
        <p className="course-instructor">{code} - {instructor}</p>
        
        {/* Rodapé do Card com as Notas e o Botão */}
        <div className="card-footer">
          <div className="footer-badges">
            <div className="badge-rating">
              <span className="star-icon">⭐</span> {rating}
            </div>
            <div className="badge-requirement">
              Pré-requisito: {req}
            </div>
          </div>
          
          <button className="btn-enroll">Inscreva-se</button>
        </div>
      </div>
    </div>
  );
}

export default CourseCard;