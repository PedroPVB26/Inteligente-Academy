import React, { useEffect, useState } from 'react';
import '../styles/Courses.css';
import CourseCard from '../components/CourseCard/CourseCard'; // Importando o componente de card que criamos antes
import roboFormado from '../assets/robo-formado.png'; // Imagem do robô com capelo

const API = import.meta.env.VITE_API_URL ?? '';
const PAGE_SIZE = 8;

const DEFAULT_FILTERS = {
    category: '',
    duration: '',
    level: '',
    language: ''
};

const FALLBACK_COURSES = [
    { id: 1, title: 'Introdução a IA', code: 'IA01', instructor: 'Robson Parmesan Bonidia', rating: '5.0', req: 'PY01', duration: '40 horas', level: 'Nível', category: 'Pequim', language: 'Python' },
    { id: 2, title: 'Aulas de Python: Do Básico ao Avançado com Projetos Reais', code: 'PY01', instructor: 'Robson Parmesan Bonidia', rating: '4.4', req: 'Nenhum', duration: '08 horas', level: 'Intermediário', category: 'Ciência de Dados', language: 'Python' },
    { id: 3, title: 'Introdução a IA', code: 'A101', instructor: 'Robson Parmesan Bonidia', rating: '5.0', req: 'PY01', duration: '02 horas', level: 'Avançado', category: 'Análise de Dados', language: 'Java' },
    { id: 4, title: 'Aulas de Python: Do Básico ao Avançado...', code: 'PY01', instructor: 'Robson Parmesan Bonidia', rating: '4.4', req: 'Nenhum', duration: '40 horas', level: 'Intermediário', category: 'Ciência de Dados', language: 'Python' },
    { id: 5, title: 'Introdução a IA', code: 'IA01', instructor: 'Robson Parmesan Bonidia', rating: '5.0', req: 'PY01', duration: '40 horas', level: 'Nível', category: 'Pequim', language: 'JavaScript' },
    { id: 6, title: 'Aulas de Python: Do Básico ao Avançado com Projetos Reais', code: 'PY01', instructor: 'Robson Parmesan Bonidia', rating: '4.4', req: 'Nenhum', duration: '08 horas', level: 'Intermediário', category: 'Ciência de Dados', language: 'Python' },
    { id: 7, title: 'Introdução a IA', code: 'A101', instructor: 'Robson Parmesan Bonidia', rating: '5.0', req: 'PY01', duration: '02 horas', level: 'Avançado', category: 'Análise de Dados', language: 'SQL' },
    { id: 8, title: 'Aulas de Python: Do Básico ao Avançado...', code: 'PY01', instructor: 'Robson Parmesan Bonidia', rating: '4.4', req: 'Nenhum', duration: '40 horas', level: 'Intermediário', category: 'Ciência de Dados', language: 'Python' },
    { id: 9, title: 'Introdução a IA', code: 'IA01', instructor: 'Robson Parmesan Bonidia', rating: '5.0', req: 'PY01', duration: '40 horas', level: 'Nível', category: 'Pequim', language: 'Java' },
    { id: 10, title: 'Aulas de Python: Do Básico ao Avançado com Projetos Reais', code: 'PY01', instructor: 'Robson Parmesan Bonidia', rating: '4.4', req: 'Nenhum', duration: '08 horas', level: 'Intermediário', category: 'Ciência de Dados', language: 'Python' },
    { id: 11, title: 'Introdução a IA', code: 'A101', instructor: 'Robson Parmesan Bonidia', rating: '5.0', req: 'PY01', duration: '02 horas', level: 'Avançado', category: 'Análise de Dados', language: 'JavaScript' },
    { id: 12, title: 'Aulas de Python: Do Básico ao Avançado...', code: 'PY01', instructor: 'Robson Parmesan Bonidia', rating: '4.4', req: 'Nenhum', duration: '40 horas', level: 'Intermediário', category: 'Ciência de Dados', language: 'Python' }
];

function CoursesPage() {
    const [courses, setCourses] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [hasMore, setHasMore] = useState(true);
    const [draftFilters, setDraftFilters] = useState(DEFAULT_FILTERS);
    const [appliedFilters, setAppliedFilters] = useState(DEFAULT_FILTERS);

    function buildQueryParams(offset = 0, filters = DEFAULT_FILTERS) {
        const params = new URLSearchParams({
            limit: String(PAGE_SIZE),
            offset: String(offset)
        });

        Object.entries(filters).forEach(([key, value]) => {
            if (value) {
                params.set(key, value);
            }
        });

        return params.toString();
    }

    function matchesFilters(course, filters) {
        return Object.entries(filters).every(([key, value]) => {
            if (!value) {
                return true;
            }

            const courseValue = String(course[key] ?? '').toLowerCase();
            return courseValue === String(value).toLowerCase();
        });
    }

    function getFilteredFallbackCourses(filters) {
        return FALLBACK_COURSES.filter((course) => matchesFilters(course, filters));
    }

    async function loadCoursesPage(offset = 0, filters = appliedFilters, replace = false) {
        setIsLoading(true);

        try {
            const response = await fetch(`${API}/courses?${buildQueryParams(offset, filters)}`);

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }

            const data = await response.json();
            const newCourses = Array.isArray(data) ? data : (data.items ?? []);
            const filteredCourses = newCourses.filter((course) => matchesFilters(course, filters));

            setCourses((currentCourses) => replace || offset === 0 ? filteredCourses : [...currentCourses, ...filteredCourses]);
            setHasMore(newCourses.length === PAGE_SIZE);
        } catch (error) {
            const fallbackCourses = getFilteredFallbackCourses(filters).slice(offset, offset + PAGE_SIZE);

            setCourses((currentCourses) => replace || offset === 0 ? fallbackCourses : [...currentCourses, ...fallbackCourses]);
            setHasMore(offset + PAGE_SIZE < getFilteredFallbackCourses(filters).length);
            console.log('Erro ao carregar cursos:', error);
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        loadCoursesPage(0, DEFAULT_FILTERS, true);
    }, []);

    function handleFilterChange(field, value) {
        setDraftFilters((currentFilters) => ({
            ...currentFilters,
            [field]: value
        }));
    }

    function handleApplyFilters() {
        setAppliedFilters(draftFilters);
        setCourses([]);
        setHasMore(true);
        loadCoursesPage(0, draftFilters, true);
    }

    function handleShowMore() {
        loadCoursesPage(courses.length, appliedFilters, false);
    }

    return (
        <div className="courses-page-container">

            {/* Banner de Destaque Superior (Roxo Arredondado) */}
            <section className="featured-banner">
                <div className="banner-text">
                    <h2 className="banner-title">REIMAGINE SEUS ESTUDOS PARA A ERA DA IA!</h2>
                    <p className="banner-subtitle">
                        Aprenda sobre inteligências artificiais desde sua origem até como implementá-las
                    </p>
                    <div className="banner-features-grid">
                        <div className="feature-item"><span>📖</span> Estude IA's</div>
                        <div className="feature-item"><span>⭐</span> Avalie as aulas</div>
                        <div className="feature-item"><span>📜</span> Garanta certificados</div>
                        <div className="feature-item"><span>📚</span> Acesso à materiais didáticos</div>
                    </div>
                </div>
                <div className="banner-image">
                    <img src={roboFormado} alt="Robô Formando" />
                </div>
            </section>

            {/* Seção de Filtros */}
            <section className="filter-section">
                <h2 className="section-main-title">Cursos e Palestras</h2>
                <p className="section-subtitle">Inicie sua jornada em Inteligência Artificial para o bem social</p>

                <div className="filter-bar">
                    <div className="dropdown-group">
                        <select value={draftFilters.category} onChange={(e) => handleFilterChange('category', e.target.value)}>
                            <option value="">Categoria</option>
                            <option value="Pequim">Pequim</option>
                            <option value="Ciência de Dados">Ciência de Dados</option>
                            <option value="Análise de Dados">Análise de Dados</option>
                        </select>
                        <select value={draftFilters.duration} onChange={(e) => handleFilterChange('duration', e.target.value)}>
                            <option value="">Duração</option>
                            <option value="02 horas">02 horas</option>
                            <option value="08 horas">08 horas</option>
                            <option value="40 horas">40 horas</option>
                        </select>
                        <select value={draftFilters.level} onChange={(e) => handleFilterChange('level', e.target.value)}>
                            <option value="">Nível</option>
                            <option value="Nível">Nível</option>
                            <option value="Intermediário">Intermediário</option>
                            <option value="Avançado">Avançado</option>
                        </select>
                        <select value={draftFilters.language} onChange={(e) => handleFilterChange('language', e.target.value)}>
                            <option value="">Linguagem</option>
                            <option value="Python">Python</option>
                            <option value="Java">Java</option>
                            <option value="JavaScript">JavaScript</option>
                            <option value="SQL">SQL</option>
                        </select>
                    </div>
                    <button type="button" className="btn-apply-filter" onClick={handleApplyFilters}>Aplicar Filtro</button>
                </div>
            </section>

            {/* Resultados e Grid de Cards */}
            <section className="results-section">
                
                {/* Mapeamento dinâmico dos cards de curso */}
                <div className="courses-grid">
                    {courses.map((course) => (
                        <CourseCard key={course.id} data={course} />
                    ))}
                </div>

                {!isLoading && courses.length === 0 && (
                    <p className="empty-results-text">Nenhum curso encontrado com os filtros selecionados.</p>
                )}

                {/* Botão Mostrar Mais Centralizado */}
                {hasMore && (
                    <div className="show-more-container">
                        <button className="btn-show-more" onClick={handleShowMore} disabled={isLoading}>
                            {isLoading ? 'Carregando...' : 'Mostrar Mais'}
                        </button>
                    </div>
                )}
            </section>

        </div>
    );
}

export default CoursesPage;