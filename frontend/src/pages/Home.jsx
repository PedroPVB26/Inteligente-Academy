import Navbar from "../components/Navbar/Navbar";

function Home() {

    return (
        <section className="home-page">
            <Navbar />
            <section className="home-hero">
                <div className="home-hero-content">
                    <h1>Aprenda de forma inteligente com o Inteligente Academy</h1>
                    <p>Explore cursos de Tecnologia, Workshops, Materiais Educativos e assista à Palestras gravadas!</p>
                    <h1>Acesse gratuitamente</h1>
                    <button className="home-cta-button">Comece Agora</button>
                    <p className="small-text">veja como funciona</p>
                </div>
                <div className="home-hero-image">
                    <img src={require('../assets/home-hero.png')} alt="IntelliGente Academy" />
                </div>
            </section>
        </section>
    )
}

export default Home;