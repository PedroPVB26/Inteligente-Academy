import CourseCard from "../components/CourseCard";

export default function Courses() {
    return (
        <main class-name="courses-page">
            <section className="courses-hero">
                <div className="hero-copy">
                    <div className="hero-text">
                        <h1>Reimagine seus estudos para a era da IA</h1>
                        <p>Aprenda sobre inteligências artificiais desde sua origem até como implementalas</p>
                        <div className="hero-topics">
                            <span className="hero-topic">Estude IA</span>
                            <span className="hero-topic">Avalie aulas</span>
                            <span className="hero-topic">Garanta certificados</span>
                            <span className="hero-topic">Acesse materiais didáricos</span>
                        </div>
                    </div>
                    <img src={require('../assets/courses-hero.png')} alt="Cursos IntelliGente" />
                </div>
            </section>
            <section className="courses-list">
                <h2>Cursos e palestras</h2>
                <p>Inicie sua jornada em inteligência artificial para o bem social!</p>

                <div className="course-filters">
                    <label htmlFor="level-filter">Nível:</label>
                    <select id="level-filter">
                        <option value="">Todos</option>
                        <option value="iniciante">Iniciante</option>
                        <option value="intermediario">Intermediário</option>
                        <option value="avancado">Avançado</option>
                    </select>
                    <label htmlFor="duration-filter">Duração:</label>
                    <select id="duration-filter">
                        <option value="">Todas</option>
                        <option value="curto">Curto</option>
                        <option value="médio">Médio</option>
                        <option value="longo">Longo</option>
                    </select>
                    <label htmlFor="category-filter">Categoria:</label>
                    <select id="category-filter">
                        <option value="">Todas</option>
                        <option value="ia">Inteligência Artificial</option>
                        <option value="data-science">Ciência de Dados</option>
                        <option value="machine-learning">Aprendizado de Máquina</option>
                    </select>
                </div>
                <div className="course-cards">
                    {courses.map(course => <CourseCard key={course.id} course={course} />)}
                </div>
            </section>
        </main>
     );
}