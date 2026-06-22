import React from 'react';
import '../styles/About.css';
// Se você tiver uma imagem conceitual para a página sobre, pode importar aqui:
// import aboutImg from '../assets/about_illustration.webp';

function About() {
  // Array simulando os desenvolvedores listados no seu footer para gerar os cards dinamicamente
  const teamMembers = [
    { id: 1, name: "Jocimar Borges Júnior", role: "Developer", img: "/assets/avatar-placeholder.png" },
    { id: 2, name: "Lucas Francisco Alves Costa", role: "Developer", img: "/assets/avatar-placeholder.png" },
    { id: 3, name: "Pedro Paulo Valent Bittencourt", role: "Developer", img: "/assets/avatar-placeholder.png" },
    { id: 4, name: "Leonardo Silva e Cruz", role: "Developer", img: "/assets/avatar-placeholder.png" },
  ];

  return (
    <div className="about-page-container">
      
      {/* Seção 1: Introdução / Quem Somos */}
      <section className="about-hero">
        <div className="about-hero-content">
          <h1 className="about-title">
            Sobre a <span className="highlight-orange">InteliGente</span> <span className="highlight-white">Academy</span>
          </h1>
          <p className="about-description">
            A InteliGente Academy nasceu com a missão de democratizar o acesso ao conhecimento focado na 
            Era da Inteligência Artificial e Tecnologia. Nosso objetivo é guiar estudantes desde os conceitos 
            mais básicos até a implementação prática de soluções reais, gerando impacto positivo para a sociedade.
          </p>
        </div>
        <div className="about-hero-image">
          {/* Usando uma caixinha com efeito glassmorphism e o logo ou mascote */}
          <div className="concept-box">
            <span className="concept-icon">💡</span>
            <h3>Educação com Propósito</h3>
          </div>
        </div>
      </section>

      {/* Seção 2: Pilares / Diferenciais (Cards com textura de vidro) */}
      <section className="about-pillars">
        <div className="pillar-card">
          <span className="pillar-icon">🤖</span>
          <h3>Foco em IA</h3>
          <p>Conteúdos atualizados constantemente acompanhando a evolução das principais ferramentas do mercado.</p>
        </div>
        
        <div className="pillar-card">
          <span className="pillar-icon">🟢</span>
          <h3>100% Gratuito</h3>
          <p>Acesso ilimitado a cursos de tecnologia, workshops e materiais didáticos sem barreiras financeiras.</p>
        </div>

        <div className="pillar-card">
          <span className="pillar-icon">📜</span>
          <h3>Certificação</h3>
          <p>Valide o seu conhecimento e impulsione o seu currículo acadêmico ou profissional com nossos certificados.</p>
        </div>
      </section>

      {/* Seção 3: Equipe de Desenvolvimento */}
      <section className="team-section">
        <h2 className="team-main-title">Quem Faz Acontecer</h2>
        <p className="team-subtitle">Conheça o time de desenvolvedores por trás da plataforma</p>
        
        <div className="team-grid">
          {teamMembers.map((member) => (
            <div key={member.id} className="member-card">
              <div className="member-avatar-box">
                {/* Substitua pelo caminho real das fotos de vocês quando tiver */}
                <img src={member.img} alt={member.name} className="member-img" />
              </div>
              <h3 className="member-name">{member.name}</h3>
              <p className="member-role">{member.role}</p>
            </div>
          ))}
        </div>
      </section>

    </div>
  );
}

export default About;