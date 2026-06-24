import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './CourseCard.css';
import tecnologiaImage from '../../assets/technology_example.webp';

// IMPORTAÇÃO DO SEU ARQUIVO DE AUTENTICAÇÃO (Ajuste o caminho conforme sua estrutura)
import { getAccessToken } from '../../services/authService'; 

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

function CourseCard({ data = {} }) {
  const navigate = useNavigate();
  const [isEnrolling, setIsEnrolling] = useState(false);

  // Extração dos dados do curso
  const {
    id: courseId,
    name = 'Curso sem título',
    description = 'Descrição não disponível',
    durationInSeconds = 0,
    publicationStatus = 'RASCUNHO',
    moduleCount = 0,
    lessonsCount = 0,
    tags = []
  } = data;

  // Formatação de exibição
  const hours = durationInSeconds ? `${Math.round(durationInSeconds / 3600)}h` : 'N/A';
  const category = tags[0]?.name ?? 'Sem categoria';
  const statusLabel = publicationStatus?.toString() ?? 'INDISPONÍVEL';

  // Redireciona para a página de detalhes do curso
  const handleNavigateToCourse = () => {
    if (courseId) {
      navigate(`/courses/${courseId}`);
    }
  };

  // Lógica de Matrícula (Cria a requisição e já aprova em seguida)
  const handleEnroll = async (e) => {
    e.stopPropagation(); // Evita que o clique no botão dispare o clique do Card inteiro
    
    if (!courseId) return;
    setIsEnrolling(true);

    // Pega o token do seu authService
    const token = getAccessToken();

    try {
      // PASSO 1: Criar a solicitação de matrícula (POST)
      const requestResponse = await fetch(`${API_URL}/enrollment-requests`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}` // Autenticação via JWT
        },
        body: JSON.stringify({ courseId: courseId }),
      });

      if (!requestResponse.ok) {
        throw new Error(`Falha ao criar solicitação de matrícula (${requestResponse.status}).`);
      }

      const requestData = await requestResponse.json();
      const enrollmentRequestId = requestData.id; // Pega o ID devolvido pelo backend

      // PASSO 2: Aprovar a solicitação automaticamente (PATCH)
      const approveResponse = await fetch(`${API_URL}/enrollment-requests/${enrollmentRequestId}/approve`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}` // Autenticação via JWT
        }
      });

      if (!approveResponse.ok) {
        throw new Error(`Falha ao aprovar a matrícula automaticamente (${approveResponse.status}).`);
      }

      // Sucesso!
      alert(`Matrícula confirmada com sucesso no curso: ${name}!`);
      navigate('/student-area'); // Redireciona o usuário para a Área do Aluno

    } catch (error) {
      console.error('Erro no fluxo de matrícula:', error);
      alert('Não foi possível concluir a sua inscrição no momento. Verifique se você já está matriculado neste curso ou tente novamente mais tarde.');
    } finally {
      setIsEnrolling(false);
    }
  };

  return (
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
          
          {/* Botão de inscrição com estado de carregamento */}
          <button 
            className="btn-enroll" 
            onClick={handleEnroll}
            disabled={isEnrolling}
            style={{ opacity: isEnrolling ? 0.6 : 1, cursor: isEnrolling ? 'not-allowed' : 'pointer' }}
          >
            {isEnrolling ? 'Matriculando...' : 'Inscreva-se'}
          </button>
        </div>
      </div>
    </div>
  );
}

export default CourseCard;