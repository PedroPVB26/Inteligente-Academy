import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import '../styles/DetailsCourses.css';

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

// Auxiliar para extrair a URL de incorporação do YouTube
const getYouTubeEmbedUrl = (url) => {
    if (!url) return null;
    const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
    const match = url.match(regExp);
    if (match && match[2].length === 11) {
        return `https://www.youtube.com/embed/${match[2]}`;
    }
    return url;
};

function formatDate(value) {
    if (!value) return '-';
    const date = new Date(value);
    if (Number.isNaN(date.getTime())) return value;
    return new Intl.DateTimeFormat('pt-BR', {
        dateStyle: 'short',
        timeStyle: 'short'
    }).format(date);
}

function formatDuration(seconds) {
    if (seconds === null || seconds === undefined) return '-';
    const totalSeconds = Number(seconds);
    if (Number.isNaN(totalSeconds)) return String(seconds);
    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    if (!hours) return `${minutes} min`;
    return `${hours}h ${minutes.toString().padStart(2, '0')}min`;
}

export default function DetailsCourses() {
    const { courseId } = useParams();
    const [course, setCourse] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');
    
    // ESTADO NOVO: Guarda a aula que está ativa no player de vídeo
    const [activeLesson, setActiveLesson] = useState(null);

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

                    // SELEÇÃO AUTOMÁTICA: Pega a primeira aula do primeiro módulo para não abrir a tela em branco
                    if (data.courseModules && data.courseModules.length > 0) {
                        const firstModule = data.courseModules[0];
                        if (firstModule.lessons && firstModule.lessons.length > 0) {
                            setActiveLesson(firstModule.lessons[0]);
                        }
                    }
                }
            } catch (error) {
                if (isActive) {
                    setErrorMessage('Não foi possível carregar os detalhes do curso.');
                }
                console.error(`Erro ao buscar detalhes do curso em ${API_URL}:`, error);
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

    // Usamos a URL de vídeo da aula selecionada (e não do curso raiz)
    const safeVideoUrl = activeLesson?.videoUrl ? getYouTubeEmbedUrl(activeLesson.videoUrl) : null;

    return (
        <main className="details-course-page">
            <section className="details-course-card">
                <div className="details-course-header">
                    <div>
                        <p className="details-course-eyebrow">Plataforma de Ensino</p>
                        <h1>{course?.name || course?.title || 'Carregando curso...'}</h1>
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
                        {/* Layout em Grid dividindo o Player da Barra Lateral de Aulas */}
                        <div className="course-viewer-layout" style={{ display: 'grid', gridTemplateColumns: '3fr 1fr', gap: '20px', marginBottom: '20px' }}>
                            
                            {/* LADO ESQUERDO: Player de Vídeo */}
                            <div className="details-course-player" style={{ background: '#111', borderRadius: '8px', overflow: 'hidden', minHeight: '450px' }}>
                                {safeVideoUrl ? (
                                    <div style={{ position: 'relative', paddingBottom: '56.25%', height: 0, overflow: 'hidden' }}>
                                        <iframe
                                            style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%' }}
                                            src={safeVideoUrl}
                                            title={activeLesson?.title}
                                            frameBorder="0"
                                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                            allowFullScreen
                                        />
                                    </div>
                                ) : (
                                    <div className="details-course-player-placeholder" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%', color: '#aaa' }}>
                                        Nenhuma aula selecionada ou sem vídeo disponível.
                                    </div>
                                )}
                                {activeLesson && (
                                    <div style={{ padding: '15px', background: '#1a1d29', color: '#fff' }}>
                                        <h3 style={{ margin: 0 }}>Assistindo agora: {activeLesson.title}</h3>
                                    </div>
                                )}
                            </div>

                            {/* LADO DIREITO: Grade de Conteúdo / Menu Lateral */}
                            <div className="course-lessons-sidebar" style={{ background: '#1a1d29', padding: '15px', borderRadius: '8px', maxHeight: '520px', overflowY: 'auto' }}>
                                <h3 style={{ color: '#fff', fontSize: '1.1rem', marginBottom: '15px', borderBottom: '1px solid #333', paddingBottom: '10px' }}>Conteúdo do Curso</h3>
                                
                                {course.courseModules && course.courseModules.length > 0 ? (
                                    course.courseModules.map((modulo) => (
                                        <div key={modulo.id} style={{ marginBottom: '15px' }}>
                                            <strong style={{ color: '#00bcd4', fontSize: '0.9rem', display: 'block', marginBottom: '5px' }}>
                                                📦 {modulo.title || modulo.name}
                                            </strong>
                                            
                                            <ul style={{ listStyle: 'none', padding: 0, margin: 0 }}>
                                                {modulo.lessons && modulo.lessons.length > 0 ? (
                                                    modulo.lessons.map((aula) => {
                                                        const isSelected = activeLesson?.id === aula.id;
                                                        return (
                                                            <li 
                                                                key={aula.id}
                                                                onClick={() => setActiveLesson(aula)}
                                                                style={{
                                                                    padding: '8px 10px',
                                                                    margin: '4px 0',
                                                                    borderRadius: '4px',
                                                                    fontSize: '0.85rem',
                                                                    cursor: 'pointer',
                                                                    background: isSelected ? '#00bcd4' : '#24283b',
                                                                    color: isSelected ? '#000' : '#fff',
                                                                    fontWeight: isSelected ? 'bold' : 'normal',
                                                                    transition: 'all 0.2s ease'
                                                                }}
                                                            >
                                                                ▶ {aula.title}
                                                            </li>
                                                        );
                                                    })
                                                ) : (
                                                    <li style={{ color: '#666', fontSize: '0.8rem', paddingLeft: '10px' }}>Sem aulas cadastradas.</li>
                                                )}
                                            </ul>
                                        </div>
                                    ))
                                ) : (
                                    <p style={{ color: '#aaa', fontSize: '0.85rem' }}>Nenhum módulo disponível.</p>
                                )}
                            </div>

                        </div>

                        {/* Metadados inferiores (mantidos do seu original) */}
                        <div className="details-course-grid">
                            <article>
                                <h2>Informações do Curso</h2>
                                <dl>
                                    <div>
                                        <dt>Nome Oficial</dt>
                                        <dd>{course.name || course.title || '-'}</dd>
                                    </div>
                                    <div>
                                        <dt>Status de Publicação</dt>
                                        <dd>{course.publicationStatus ?? '-'}</dd>
                                    </div>
                                    <div>
                                        <dt>Duração Total Estimada</dt>
                                        <dd>{formatDuration(course.durationInSeconds)}</dd>
                                    </div>
                                </dl>
                            </article>

                            <article>
                                <h2>Histórico e Registro</h2>
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
                                        <dt>Link da Aula Atual</dt>
                                        <dd className="details-course-break-word" style={{ color: '#00bcd4' }}>
                                            {activeLesson?.videoUrl ?? '-'}
                                        </dd>
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