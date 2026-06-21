export default function DetailsCourses() {
    return (
        <main className="details-course-page">
            <section className="course-player-section">
                <div className="course-player">
                    <iframe width="560" height="315" src="https://www.youtube.com/embed/dQw4w9WgXcQ" title="YouTube video player" frameBorder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowFullScreen></iframe>
                </div>
                <div className="course-info">
                    <h1>{course.name}</h1>
                    <p>{course.description}</p>
                    <div className="course-modules">
                        <h2>Módulos</h2>
                        <ul>
                            {course.modules.map(module => (
                                <li key={module.id}>
                                    <h3>{module.name}</h3>
                                    <p>Duração: {module.duration}</p>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </section>
            <section className="course-about-section">
                <h2>Sobre o Curso</h2>
                <p>{course.about}</p>
                <h2>Material Adicional</h2>
                <p>{course.additionalMaterial}</p>
            </section>
        </main>
    );
}