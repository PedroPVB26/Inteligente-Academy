import React from 'react';
import './CourseCard.css';

function CourseCard() {
  return (
    <div className="course-card">
      {/* Parte Superior: Imagem e Tags Flutuantes */}
      <div className="card-image-container">
        <img 
          src="/assets/curso-ia.png" 
          alt="Introdução a IA" 
          className="card-image" 
        />
        
        {/* Tags que ficam por cima da imagem */}
        <div className="tags-left">
          <span className="tag-badge badge-green">Nível</span>
          <span className="tag-badge badge-blue">Categoria</span>
        </div>
        
        <div className="tag-right">
          <span className="tag-badge badge-time">
            <span className="clock-icon">🕒</span> 40 horas
          </span>
        </div>
      </div>

      {/* Parte Inferior: Informações do Curso */}
      <div className="card-info">
        <h3 className="course-title">Introdução a IA</h3>
        <p className="course-instructor">IA01 - Robson Parmesan Bonidia</p>
        
        {/* Rodapé do Card com as Notas e o Botão */}
        <div className="card-footer">
          <div className="footer-badges">
            <div className="badge-rating">
              <span className="star-icon">⭐</span> 5.0
            </div>
            <div className="badge-requirement">
              Pré-requisito: PY01
            </div>
          </div>
          
          <button className="btn-enroll">Inscreva-se</button>
        </div>
      </div>
    </div>
  );
}

export default CourseCard;