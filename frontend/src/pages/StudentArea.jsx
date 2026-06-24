import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
// IMPORTANDO O SEU ARQUIVO DE AUTENTICAÇÃO (Ajuste o caminho conforme sua pasta)
import { getCurrentUser, getAccessToken } from '../services/authService.js'; 

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

export default function StudentArea() {
    const navigate = useNavigate();
    const [enrollments, setEnrollments] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');

    // Pegamos o usuário logado usando a sua função!
    const user = getCurrentUser();

    useEffect(() => {
        // Se a sua função retornar null (sem token ou expirado), não faz a busca
        if (!user || !user.id) {
            setIsLoading(false);
            setErrorMessage('Usuário não autenticado.');
            return;
        }

        async function loadUserEnrollments() {
            setIsLoading(true);
            try {
                // Pegamos o token limpo usando a sua função
                const token = getAccessToken();

                // Usamos o user.id dinâmico na URL
                const response = await fetch(`${API_URL}/enrollments/users/${user.id}`, {
                    headers: {
                        'Authorization': `Bearer ${token}` // Passando o token para o Spring Security
                    }
                });
                
                if (!response.ok) {
                    throw new Error(`Erro ao buscar matrículas (${response.status}).`);
                }
                
                const data = await response.json();
                setEnrollments(Array.isArray(data) ? data : []);
            } catch (error) {
                console.error('Erro na Área do Aluno:', error);
                setErrorMessage('Não foi possível carregar os seus cursos.');
            } finally {
                setIsLoading(false);
            }
        }

        loadUserEnrollments();
    }, []);

    return (
        <main className="details-course-page" style={{ padding: '20px' }}>
            <section className="details-course-card" style={{ maxWidth: '1000px', margin: '0 auto', background: '#1a1d29', padding: '30px', borderRadius: '12px' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px', borderBottom: '1px solid #333', paddingBottom: '15px' }}>
                    <div>
                        <h1 style={{ color: '#fff', margin: 0 }}>Minhas Matrículas</h1>
                        <p style={{ color: '#aaa', margin: '5px 0 0 0' }}>Estes são os cursos liberados para você.</p>
                    </div>
                    <Link to="/courses" style={{ color: '#00bcd4', textDecoration: 'none', fontWeight: 'bold' }}>
                        🌐 Ver mais cursos
                    </Link>
                </div>

                {isLoading && <p style={{ color: '#fff' }}>Carregando seus cursos...</p>}
                
                {!isLoading && errorMessage && (
                    <p style={{ color: '#ff4a4a' }}>{errorMessage}</p>
                )}

                {!isLoading && !errorMessage && enrollments.length === 0 && (
                    <div style={{ textAlign: 'center', padding: '40px 0', color: '#aaa' }}>
                        <p>Você ainda não está matriculado em nenhum curso.</p>
                        <Link to="/courses" style={{ display: 'inline-block', marginTop: '15px', background: '#00bcd4', color: '#000', padding: '10px 20px', borderRadius: '4px', textDecoration: 'none', fontWeight: 'bold' }}>
                            Escolher meu primeiro curso
                        </Link>
                    </div>
                )}

                {!isLoading && enrollments.length > 0 && (
                    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px' }}>
                        {enrollments.map((item) => (
                            <div 
                                key={item.id} 
                                style={{ background: '#24283b', borderRadius: '8px', padding: '20px', display: 'flex', flexDirection: 'column', justifyContent: 'space-between', border: '1px solid #333' }}
                            >
                                <div>
                                    {/* Exibe o nome do curso vindo da matrícula */}
                                    <h3 style={{ color: '#fff', marginTop: 0, marginBottom: '10px' }}>{item.course?.name || item.courseName || 'Curso Cadastrado'}</h3>
                                    
                                    <p style={{ fontSize: '0.8rem', color: '#aaa' }}>
                                        Matriculado em: {item.requestedAt ? new Date(item.requestedAt).toLocaleDateString('pt-BR') : '-'}
                                    </p>
                                </div>

                                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: '20px' }}>
                                    <span style={{ 
                                        fontSize: '0.75rem', 
                                        padding: '4px 8px', 
                                        borderRadius: '4px',
                                        fontWeight: 'bold',
                                        background: '#81c784',
                                        color: '#000'
                                    }}>
                                        MATRICULADO
                                    </span>

                                    {/* Usa o courseId ou item.course.id para levar o aluno para as aulas */}
                                    <button 
                                        onClick={() => navigate(`/courses/${item.courseId || item.course?.id}`)}
                                        style={{ background: '#00bcd4', color: '#000', border: 'none', padding: '6px 12px', borderRadius: '4px', fontWeight: 'bold', cursor: 'pointer' }}
                                    >
                                        Acessar Curso →
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </section>
        </main>
    );
}