import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import '../styles/DetailsCourses.css';

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

function formatDate(value) {
    if (!value) {
        return '-';
    }

    const date = new Date(value);
    if (Number.isNaN(date.getTime())) {
        return value;
    }

    return new Intl.DateTimeFormat('pt-BR', {
        dateStyle: 'short',
        timeStyle: 'short'
    }).format(date);
}

function formatDuration(seconds) {
    if (seconds === null || seconds === undefined) {
        return '-';
    }

    const totalSeconds = Number(seconds);
    if (Number.isNaN(totalSeconds)) {
        return String(seconds);
    }

    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);

    if (!hours) {
        return `${minutes} min`;
    }

    return `${hours}h ${minutes.toString().padStart(2, '0')}min`;
}

export default function DetailsCourses() {
    const { courseId } = useParams();
    const [course, setCourse] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        let isActive = true;

        async function loadCourseDetails() {
            setIsLoading(true);
            setErrorMessage('');

            try {
                const response = await fetch(`${API_URL}/courses/${courseId}`);

                if (!response.ok) {
                    throw new Error(`Falha ao carregar o curso (${response.status})`);
                }

                const data = await response.json();

                if (isActive) {
                    setCourse(data);
                }
            } catch (error) {
                if (isActive) {
                    setErrorMessage('Não foi possível carregar os detalhes do curso.');
                }

                console.error('Erro ao buscar detalhes do curso:', error);
            } finally {
                if (isActive) {
                    setIsLoading(false);
                }
            }
        }

        if (courseId) {
            loadCourseDetails();
        } else {
            setErrorMessage('ID do curso não informado.');
            setIsLoading(false);
        }

        return () => {
            isActive = false;
        };
    }, [courseId]);

    return (
        <main className="details-course-page">
            <section className="details-course-card">
                <div className="details-course-header">
                    <div>
                        <p className="details-course-eyebrow">Detalhes do conteúdo</p>
                        <h1>{course?.title ?? 'Carregando curso...'}</h1>
                    </div>

                    <Link className="details-course-back-link" to="/courses">
                        Voltar para cursos
                    </Link>
                </div>

                {isLoading && <p className="details-course-feedback">Carregando detalhes...</p>}

                {!isLoading && errorMessage && (
                    <p className="details-course-feedback details-course-feedback--error">{errorMessage}</p>
                )}

                {!isLoading && course && !errorMessage && (
                    <>
                        <div className="details-course-player">
                            {course.videoUrl ? (
                                <iframe
                                    src={course.videoUrl}
                                    title={course.title}
                                    frameBorder="0"
                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                    allowFullScreen
                                />
                            ) : (
                                <div className="details-course-player-placeholder">
                                    Sem vídeo disponível para este item.
                                </div>
                            )}
                        </div>

                        <div className="details-course-grid">
                            <article>
                                <h2>Informações</h2>
                                <dl>
                                    <div>
                                        <dt>ID</dt>
                                        <dd>{course.id ?? '-'}</dd>
                                    </div>
                                    <div>
                                        <dt>Título</dt>
                                        <dd>{course.title ?? '-'}</dd>
                                    </div>
                                    <div>
                                        <dt>Posição</dt>
                                        <dd>{course.position ?? '-'}</dd>
                                    </div>
                                    <div>
                                        <dt>Status</dt>
                                        <dd>{course.publicationStatus ?? '-'}</dd>
                                    </div>
                                    <div>
                                        <dt>Duração</dt>
                                        <dd>{formatDuration(course.durationInSeconds)}</dd>
                                    </div>
                                </dl>
                            </article>

                            <article>
                                <h2>Datas</h2>
                                <dl>
                                    <div>
                                        <dt>Criado em</dt>
                                        <dd>{formatDate(course.createdAt)}</dd>
                                    </div>
                                    <div>
                                        <dt>Última atualização</dt>
                                        <dd>{formatDate(course.modifiedAt)}</dd>
                                    </div>
                                    <div>
                                        <dt>Vídeo</dt>
                                        <dd className="details-course-break-word">{course.videoUrl ?? '-'}</dd>
                                    </div>
                                </dl>
                            </article>
                        </div>
                    </>
                )}
            </section>
        </main>
    );
}