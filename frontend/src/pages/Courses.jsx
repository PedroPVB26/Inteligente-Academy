import React, { useState } from 'react';
import '../styles/CoursesPage.css';
import CourseCard from './CourseCard'; // Importando o componente de card que criamos antes

function CoursesPage() {
  // Simulando uma lista de cursos vinda de um estado ou API
  const [courses, setCourses] = useState([
    { id: 1, title: 'Introdução a IA', code: 'IA01', instructor: 'Robson Parmesan Bonidia', rating: '5.0', req: 'PY01', hours: '40 horas', level: 'Nível', category: 'Categoria' },
    { id: 2, title: 'Aulas de Python: Do Básico ao Avançado com Projetos Reais', code: 'PY01', instructor: 'Robson Parmesan Bonidia', rating: '4.4', req: 'Nenhum', hours: '08 horas', level: 'Intermediário', category: 'Ciência de Dados' },
    { id: 3, title: 'Introdução a IA', code: 'A101', instructor: 'Robson Parmesan Bonidia', rating: '5.0', req: 'PY01', hours: '02 horas', level: 'Avançado', category: 'Análise de Dados' },
    { id: 4, title: 'Aulas de Python: Do Básico ao Avançado...', code: 'PY01', instructor: 'Robson Parmesan Bonidia', rating: '4.4', req: 'Nenhum', hours: '40 horas', level: 'Intermediário', category: 'Ciência de Dados' },
    // Adicione quantos cursos quiser, o grid vai se ajustar sozinho!
  ]);

  return (
    <div className="courses-page-container">
      
      {/* Banner de Destaque Superior (Roxo Arredondado) */}
      <section className="featured-banner">
        <div className="banner-text">
          <h2 className="banner-title">REIMAGINE SEUS ESTUDOS PARA A ERA DA IA!</h2>
          <p className="banner-subtitle">
            Aprenda sobre inteligências artificiais desde sua origem até como implementá-las
          </p>
          <div className="banner-features-grid">
            <div className="feature-item"><span>📖</span> Estude IA's</div>
            <div className="feature-item"><span>⭐</span> Avalie as aulas</div>
            <div className="feature-item"><span>📜</span> Garanta certificados</div>
            <div className="feature-item"><span>📚</span> Acesso à materiais didáticos</div>
          </div>
        </div>
        <div className="banner-image">
          <img src="/assets/robo-capelo.png" alt="Robô Formando" />
        </div>
      </section>

      {/* Seção de Filtros */}
      <section className="filter-section">
        <h2 className="section-main-title">Cursos e Palestras</h2>
        <p className="section-subtitle">Inicie sua jornada em Inteligência Artificial para o bem social</p>
        
        <div className="filter-bar">
          <div className="dropdown-group">
            <select><option>Categoria</option></select>
            <select><option>Duração</option></select>
            <select><option>Nível</option></select>
            <select><option>Linguagem</option></select>
          </div>
          <button className="btn-apply-filter">Aplicar Filtro</button>
        </div>
      </section>

      {/* Resultados e Grid de Cards */}
      <section className="results-section">
        <div className="results-header">
          <div className="results-titles">
            <h3>Top Resultados</h3>
            <p>Sabemos o que é melhor para você. As melhores opções para você.</p>
          </div>
          <select className="sort-dropdown">
            <option>Mais popular</option>
          </select>
        </div>

        {/* Mapeamento dinâmico dos cards de curso */}
        <div className="courses-grid">
          {courses.map((course) => (
            <CourseCard key={course.id} data={course} />
          ))}
        </div>

        {/* Botão Show More Centralizado */}
        <div className="show-more-container">
          <button className="btn-show-more">Show More</button>
        </div>
      </section>

    </div>
  );
}

export default CoursesPage;