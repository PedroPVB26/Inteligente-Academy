import React from 'react';
import '../styles/About.css';
import { useTranslation } from "react-i18next";

function About() {
  // Array simulando os desenvolvedores listados no seu footer para gerar os cards dinamicamente
  const teamMembers = [
    { id: 1, name: "Jocimar Borges Júnior", github: "https://www.github.com/jocimarbj",linkedin: "https://www.linkedin.com/in/jocimarbj/", role: "Developer", img: "https://media.licdn.com/dms/image/v2/D4D03AQHFOGDKdruNMg/profile-displayphoto-scale_400_400/B4DZx1HjCeKYAk-/0/1771491441129?e=1783555200&v=beta&t=aCkkvL3N1xHp5yUbAQ9_WPgu5qb6pa-vo57APVR7Xzc" },
    { id: 2, name: "Lucas Francisco Alves Costa", github: "https://github.com/LucasFranciscoAlvesCosta",linkedin: "https://www.linkedin.com/in/lucas-francisco-alves-costa-12b2ab327/", role: "Developer", img: "https://media.licdn.com/dms/image/v2/D4D03AQFGeToggUNfgQ/profile-displayphoto-shrink_400_400/profile-displayphoto-shrink_400_400/0/1725422726558?e=1783555200&v=beta&t=RUnPji8R7SpxoOp8on91mk6z4ZhTwDXbhuDhtqfZjHA" },
    { id: 3, name: "Pedro Paulo Valent Bittencourt", github: "https://github.com/PedroPVB26", linkedin: "https://www.linkedin.com/in/pedro-bittencourt-883867275/", role: "Developer", img: "https://media.licdn.com/dms/image/v2/D4D35AQFbbNQbjnMsGw/profile-framedphoto-shrink_800_800/profile-framedphoto-shrink_800_800/0/1720750104193?e=1782763200&v=beta&t=BeioH2KTu56KV8PRhXNmnz43eOe5DLkcinMaHiymxiQ" },
    { id: 4, name: "Leonardo Silva e Cruz", github: "https://www.github.com/hiperd", linkedin: "https://www.linkedin.com/in/leonardosilvaecruz/", role: "Developer", img: "https://media.licdn.com/dms/image/v2/D4D03AQGFypLUr2LmIA/profile-displayphoto-scale_400_400/B4DZ1KYG3VIYAs-/0/1775069333065?e=1783555200&v=beta&t=YpItziQ-aFRbDQvS2dUq2peVCCGs2ZMIKHsBL_fT39I" },
  ];
  const { t } = useTranslation();

  return (
    <div className="about-page-container">
      
      {/* Seção 1: Introdução / Quem Somos */}
      <section className="about-hero">
        <div className="about-hero-content">
          <h1 className="about-title">
            {t("about.title")} <span className="highlight-orange">InteliGente</span> <span className="highlight-white">Academy</span>
          </h1>
          <p className="about-description">
          {t("about.description")}
          </p>
        </div>
        <div className="about-hero-image">
          {/* Usando uma caixinha com efeito glassmorphism e o logo ou mascote */}
          <div className="concept-box">
            <span className="concept-icon">💡</span>
            <h3>{t("about.concept")}</h3>
          </div>
        </div>
      </section>

      {/* Seção 2: Pilares / Diferenciais (Cards com textura de vidro) */}
      <section className="about-pillars">
        <div className="pillar-card">
          <span className="pillar-icon">🤖</span>
          {t("about.pillars.text-ia")}
          <h3>{t("about.pillars.title-ia")}</h3>
          <p></p>
        </div>
        
        <div className="pillar-card">
          <span className="pillar-icon">🟢</span>
          <h3>{t("about.pillars.title-free")}</h3>
          <p>{t("about.pillars.text-free")}</p>
        </div>

        <div className="pillar-card">
          <span className="pillar-icon">📜</span>
          <h3>{t("about.pillars.title-certification")}</h3>
          <p>{t("about.pillars.text-certification")}</p>
        </div>
      </section>

      {/* Seção 3: Equipe de Desenvolvimento */}
      <section className="team-section">
        <h2 className="team-main-title">{t("about.team-dev.title")}</h2>
        <p className="team-subtitle">{t("about.team-dev.subtitle")}</p>
        
        <div className="team-grid">
          {teamMembers.map((member) => (
            <div key={member.id} className="member-card">
              <div className="member-avatar-box">
                {/* Substitua pelo caminho real das fotos de vocês quando tiver */}
                <a href={member.linkedin} target="_blank" rel="noopener noreferrer">
                  <img src={member.img} alt={member.name} className="member-img" />
                </a>
              </div>
              <h3 className="member-name">{member.name}</h3>
              <p className="member-role">{member.role}</p>
              <a href={member.github} target="_blank" rel="noopener noreferrer"> Github</a>
            </div>
          ))}
        </div>
      </section>

    </div>
  );
}

export default About;