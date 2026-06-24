import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // 1. Importa o hook de navegação
import home_hero from "../assets/hero_intelirob.webp";
import ad_certificado from "../assets/ad_certificado.webp";
import ad_cursos from "../assets/ad_cursos.webp";
import carrossel1_livro from "../assets/carrossel1_livro.webp";
import "../styles/Home.css";

function Home() {
  const navigate = useNavigate(); // 2. Inicializa o navigate
  const [activeIndex, setActiveIndex] = useState(0);

  const carouselItems = [
    { id: "carrossel", src: carrossel1_livro, alt: "Livro Ciência de Dados" },
  ];

  const handlePrev = () => {
    setActiveIndex((current) => (current - 1 + carouselItems.length) % carouselItems.length);
  };

  const handleNext = () => {
    setActiveIndex((current) => (current + 1) % carouselItems.length);
  };

  return (
    <>
      <section className="hero-container">
      <div className="hero-wave-wrap" aria-hidden="true">
        <svg className="hero-waves" viewBox="0 24 150 28" preserveAspectRatio="none">
          <defs>
            <path
              id="home-wave-path"
              d="M-160 44c30 0 58-18 88-18s58 18 88 18 58-18 88-18 58 18 88 18v44h-352z"
            />
          </defs>
          <g className="hero-wave hero-wave-1">
            <use href="#home-wave-path" x="48" y="0" />
          </g>
          <g className="hero-wave hero-wave-2">
            <use href="#home-wave-path" x="48" y="3" />
          </g>
          <g className="hero-wave hero-wave-3">
            <use href="#home-wave-path" x="48" y="5" />
          </g>
          <g className="hero-wave hero-wave-4">
            <use href="#home-wave-path" x="48" y="7" />
          </g>
        </svg>
      </div>

      <div className="hero-content">
        <h1 className="hero-title">
          Aprenda de Forma <span className="highlight-orange">InteliGente</span> com o <span className="highlight-white">InteliGente Academy!</span>
        </h1>
        
        <p className="hero-description">
          Explore cursos de Tecnologia, Workshops, Materiais Educativos e assista à Palestras gravadas!
        </p>
        
        <div className="hero-action-box">
          <h2 className="hero-subtitle">
            Acesse <span className="highlight-green">Gratuitamente!</span>
          </h2>
          
          <div className="hero-buttons">
            {/* 3. Adiciona o onClick direcionando para a rota de cursos */}
            <button className="btn-start" onClick={() => navigate("/courses")}>
              Começar!
            </button>
            
            <a href="#funcionamento" className="btn-watch">
              <span className="play-icon">▶</span>
              Veja como funciona!
            </a>
          </div>
        </div>
      </div>

      <div className="hero-image-wrapper">
        <img src={home_hero} alt="Robô Inteligente Academy" className="hero-main-image" />
      </div>
      </section>
    
    <section className="home-carousel-section">
      <div className="home-carousel-box">
        <button type="button" className="carousel-nav prev" onClick={handlePrev} aria-label="Anterior">
          ◀
        </button>
        <div className="home-carousel-wrapper">
          <div className="home-carousel-track" style={{ transform: `translateX(-${activeIndex * 100}%)` }}>
            {carouselItems.map((item) => (
              <div className="home-carousel-card" id={item.id} key={item.id}>
                <img src={item.src} alt={item.alt} />
              </div>
            ))}
          </div>
        </div>
        <button type="button" className="carousel-nav next" onClick={handleNext} aria-label="Próximo">
          ▶
        </button>
      </div>

      <div className="home-carousel-static-cards">
        <div className="home-carousel-card" id="ad-cursos">
          <img src={ad_cursos} alt="Veja todos os cursos" />
        </div>
        <div className="home-carousel-card" id="ad-certificado">
          <img src={ad_certificado} alt="Obtenha seu certificado" />
        </div>
      </div>
    </section>
  </>
  );
}

export default Home;