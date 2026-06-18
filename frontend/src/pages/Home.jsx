import { Link } from "react-router-dom";
import heroRobot from "../assets/hero_intelirob.webp";
import carouselBook from "../assets/carrossel1_livro.webp";
import academyAd from "../assets/ad_cursos.webp";
import certificateAd from "../assets/ad_certificado.webp";
function Home() {
  return (
    <main className="home-page">
      <section className="home-hero">
        <div className="hero-copy">
          <span className="hero-badge">Acesse Gratuitamente!</span>
          <h1>
            Aprenda de forma <strong>InteliGente</strong>
            <br />
            com o IntelliGente Academy!
          </h1>
          <p>
            Explore cursos de Tecnologia, Workshops, Materiais Educativos e assista a Palestras gravadas!
          </p>
          <div className="hero-actions">
            <Link className="button button-primary" to="/admin/courses">
              Começar!
            </Link>
            <a className="button button-secondary" href="#learn-more">
              Veja como funciona!
            </a>
          </div>
        </div>
        <div className="hero-media">
          <img src={heroRobot} alt="Robô IntelliGente" />
        </div>
      </section>

      <section className="home-carousel">
        <div className="carousel-card">
          <div>
            <span className="carousel-label">TOP 10 em Probabilidade e Estatística</span>
            <h2>Não sabe por onde começar?</h2>
            <p>
              Leia o livro de Ciência de Dados e aprenda os principais fundamentos e aplicações de uma IA.
            </p>
            <button className="button button-primary">Adquira já</button>
          </div>
          <img src={carouselBook} alt="Livro de Ciência de Dados" />
        </div>
      </section>

      <section className="home-features" id="learn-more">
        <div className="feature-copy">
          <h2>Escolha o conteúdo e comece a aprender hoje.</h2>
          <p>É gratuito. É rápido.</p>
          <Link className="button button-secondary" to="/admin/courses">
            Ver todos os cursos
          </Link>
        </div>
        <img src={academyAd} alt="Cursos IntelliGente" />
      </section>

      <section className="home-certificate">
        <div className="certificate-card">
          <img src={certificateAd} alt="Certificado IntelliGente" />
          <div className="certificate-copy">
            <h2>
              Obtenha seu Certificado, aprimore seu conhecimento e traga inovação para sua carreira.
            </h2>
            <p>
              Os certificados são gerados após a finalização dos cursos e podem ser validados no IntelliGente Academy.
              Sempre On-line. Sempre disponível para você!
            </p>
            <Link className="button button-primary" to="/admin/courses">
              Emitir e validar certificado
            </Link>
          </div>
        </div>
      </section>
    </main>
  );
}

export default Home;