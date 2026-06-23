import React, { useEffect, useState } from 'react';
import '../styles/Courses.css';
import CourseCard from '../components/CourseCard/CourseCard'; // Importando o componente de card que criamos antes
import roboFormado from '../assets/robo-formado.png'; // Imagem do robô com capelo

const API = import.meta.env.VITE_API_URL ?? window.location.origin;
const PAGE_SIZE = 8;

const DEFAULT_FILTERS = {
    tagId: '',
    duration: '',
    level: '',
    language: ''
};

function CoursesPage() {
    const [courses, setCourses] = useState([]);
    const [tags, setTags] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [hasMore, setHasMore] = useState(true);
    const [draftFilters, setDraftFilters] = useState(DEFAULT_FILTERS);
    const [appliedFilters, setAppliedFilters] = useState(DEFAULT_FILTERS);

    function buildQueryParams(offset = 0, filters = DEFAULT_FILTERS) {
        const params = new URLSearchParams({
            limit: String(PAGE_SIZE),
            offset: String(offset)
        });

        if (filters.tagId) {
            params.set('tagId', filters.tagId);
        }

        return params.toString();
    }

    async function loadTags() {
        try {
            const response = await fetch(`${API}/tags`);
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }
            const tagsData = await response.json();
            setTags(tagsData);
        } catch (error) {
            console.error('Erro ao carregar categorias:', error);
        }
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
            setCourses((currentCourses) => replace || offset === 0 ? newCourses : [...currentCourses, ...newCourses]);
            setHasMore(newCourses.length === PAGE_SIZE);
        } catch (error) {
            setCourses((currentCourses) => replace || offset === 0 ? [] : currentCourses);
            setHasMore(false);
            console.log('Erro ao carregar cursos:', error);
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        loadTags();
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
                        <select value={draftFilters.tagId} onChange={(e) => handleFilterChange('tagId', e.target.value)}>
                            <option value="">Categoria</option>
                            {tags.map((tag) => (
                                <option key={tag.id} value={tag.id}>{tag.name}</option>
                            ))}
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