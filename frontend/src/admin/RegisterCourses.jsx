import { useState, useEffect } from 'react';
import './RegisterCourses.css';

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

// Função auxiliar para extrair o ID do vídeo do YouTube e gerar a miniatura
const getYouTubeThumbnail = (url) => {
  if (!url) return null;
  // Expressão regular aceita tanto links normais quanto links de compartilhamento ou embed
  const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
  const match = url.match(regExp);
  if (match && match[2].length === 11) {
    return `https://img.youtube.com/vi/${match[2]}/hqdefault.jpg`;
  }
  return null;
};

// OPCIONAL: gerar o link de EMBED para usar num <iframe> futuramente
const getYouTubeEmbedUrl = (url) => {
  if (!url) return null;
  const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
  const match = url.match(regExp);
  if (match && match[2].length === 11) {
    return `https://www.youtube.com/embed/${match[2]}`;
  }
  return null;
};

function RegisterCourses({ onCreationSuccess }) {
  // Estados do formulário base
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  
  // Estados para Módulos e Aulas
  const [modules, setModules] = useState([]);
  
  // Estados para Avaliações (Modal Flutuante)
  const [isAssessmentModalOpen, setIsAssessmentModalOpen] = useState(false);
  const [assessments, setAssessments] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState('');

  // Estados de Listagem e Controle de Interface
  const [courses, setCourses] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  // Estados para os Collapsibles (guardam quais IDs estão expandidos)
  const [expandedCourses, setExpandedCourses] = useState({});
  const [expandedModules, setExpandedModules] = useState({});
  const [showAllCourses, setShowAllCourses] = useState(false);

  useEffect(() => {
    fetchCourses();
  }, []);

  const visibleCourses = showAllCourses ? courses : courses.slice(0, 3);

  const toggleShowAllCourses = () => {
    setShowAllCourses(prev => !prev);
  };

  async function fetchCourseDetails(courseId) {
    try {
      const response = await fetch(`${API_URL}/courses/${courseId}`);
      if (response.ok) {
        const courseDetails = await response.json();
        setCourses(prev => prev.map(course => course.id === courseId ? courseDetails : course));
      }
    } catch (error) {
      console.error('Error fetching course details:', error);
    }
  }

  async function fetchCourses() {
    try {
      const response = await fetch(`${API_URL}/courses`);
      if (response.ok) {
        const data = await response.json();
        setCourses(data);
      }
    } catch (error) {
      console.error('Error fetching courses:', error);
    }
  }

  /* --- FUNÇÕES PARA GERENCIAR MÓDULOS E AULAS NO FORMULÁRIO --- */
  const handleAddModule = () => {
    setModules([...modules, { id: Date.now(), title: '', lessons: [] }]);
  };

  const handleModuleTitleChange = (moduleId, newTitle) => {
    setModules(modules.map(mod => mod.id === moduleId ? { ...mod, title: newTitle } : mod));
  };

  const handleAddLesson = (moduleId) => {
    setModules(modules.map(mod => {
      if (mod.id === moduleId) {
        return { ...mod, lessons: [...mod.lessons, { id: Date.now(), title: '', youtubeUrl: '' }] };
      }
      return mod;
    }));
  };

  const handleLessonChange = (moduleId, lessonId, field, value) => {
    setModules(modules.map(mod => {
      if (mod.id === moduleId) {
        const updatedLessons = mod.lessons.map(lesson => 
          lesson.id === lessonId ? { ...lesson, [field]: value } : lesson
        );
        return { ...mod, lessons: updatedLessons };
      }
      return mod;
    }));
  };

  /* --- FUNÇÕES PARA AVALIAÇÃO --- */
  const handleAddAssessmentQuestion = () => {
    if (currentQuestion.trim()) {
      setAssessments([...assessments, { id: Date.now(), question: currentQuestion }]);
      setCurrentQuestion('');
    }
  };

  /* --- FUNÇÕES PARA EXPANDIR/RETRAIR A LISTA --- */
  const toggleCourse = async (courseId) => {
    const isOpen = !!expandedCourses[courseId];
    setExpandedCourses(prev => ({ ...prev, [courseId]: !prev[courseId] }));
    if (!isOpen) {
      const course = courses.find(c => c.id === courseId);
      if (course && !course.courseModules) {
        await fetchCourseDetails(courseId);
      }
    }
  };

  const toggleModule = (moduleId) => {
    setExpandedModules(prev => ({ ...prev, [moduleId]: !prev[moduleId] }));
  };

  const handleDeleteCourse = async (courseId) => {
    if (!window.confirm('Deseja excluir este curso e todo o seu conteúdo?')) return;

    try {
      const response = await fetch(`${API_URL}/courses/${courseId}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        setCourses(prev => prev.filter(course => course.id !== courseId));
        setSuccessMessage('Curso excluído com sucesso.');
      } else {
        const errorData = await response.json().catch(() => ({}));
        setErrorMessage(errorData.message || 'Erro ao excluir o curso.');
      }
    } catch (error) {
      console.error('Error deleting course:', error);
      setErrorMessage('Não foi possível conectar ao servidor para excluir o curso.');
    }
  };

  const handleDeleteModule = async (courseId, moduleId) => {
    if (!window.confirm('Deseja excluir este módulo e todas as suas aulas?')) return;

    try {
      const response = await fetch(`${API_URL}/courses/${courseId}/modules/${moduleId}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        setCourses(prev => prev.map(course => {
          if (course.id !== courseId) return course;
          return {
            ...course,
            courseModules: course.courseModules?.filter(module => module.id !== moduleId),
          };
        }));
        setExpandedModules(prev => {
          const next = { ...prev };
          delete next[moduleId];
          return next;
        });
        setSuccessMessage('Módulo excluído com sucesso.');
      } else {
        const errorData = await response.json().catch(() => ({}));
        setErrorMessage(errorData.message || 'Erro ao excluir o módulo.');
      }
    } catch (error) {
      console.error('Error deleting module:', error);
      setErrorMessage('Não foi possível conectar ao servidor para excluir o módulo.');
    }
  };

  const handleDeleteLesson = async (courseId, moduleId, lessonId) => {
    if (!window.confirm('Deseja excluir esta aula?')) return;

    try {
      const response = await fetch(`${API_URL}/courses/${courseId}/modules/${moduleId}/lessons/${lessonId}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        setCourses(prev => prev.map(course => {
          if (course.id !== courseId) return course;
          return {
            ...course,
            courseModules: course.courseModules?.map(module => {
              if (module.id !== moduleId) return module;
              return {
                ...module,
                lessons: module.lessons?.filter(lesson => lesson.id !== lessonId),
              };
            }),
          };
        }));
        setSuccessMessage('Aula excluída com sucesso.');
      } else {
        const errorData = await response.json().catch(() => ({}));
        setErrorMessage(errorData.message || 'Erro ao excluir a aula.');
      }
    } catch (error) {
      console.error('Error deleting lesson:', error);
      setErrorMessage('Não foi possível conectar ao servidor para excluir a aula.');
    }
  };

  /* --- SUBMISSÃO DO FORMULÁRIO --- */
  async function handleSubmit(event) {
    event.preventDefault();

    if (!name.trim() || !description.trim()) {
      setErrorMessage('Por favor, preencha o nome e descrição do curso.');
      return;
    }

    setIsLoading(true);
    setErrorMessage('');
    setSuccessMessage('');

    const coursePayload = {
      name,
      description,
    };

    try {
      const courseResponse = await fetch(`${API_URL}/courses`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(coursePayload),
      });

      if (!courseResponse.ok) {
        const errorData = await courseResponse.json().catch(() => ({}));
        setErrorMessage(errorData.message || 'Erro ao cadastrar curso no servidor.');
        return;
      }

      const createdCourse = await courseResponse.json();
      const courseId = createdCourse.id;

      for (const [modIndex, mod] of modules.entries()) {
        const modulePayload = {
          title: mod.title,
          description: mod.description || `Módulo sobre ${mod.title}`,
          position: modIndex + 1,
        };

        const moduleResponse = await fetch(`${API_URL}/courses/${courseId}/modules`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(modulePayload),
        });

        if (!moduleResponse.ok) {
          const errorData = await moduleResponse.json().catch(() => ({}));
          throw new Error(errorData.message || 'Erro ao cadastrar módulo no servidor.');
        }

        const createdModule = await moduleResponse.json();
        const moduleId = createdModule.id;

        for (const [lesIndex, les] of mod.lessons.entries()) {
          const lessonPayload = {
            title: les.title,
            position: lesIndex + 1,
            durationInSeconds: 900,
            videoUrl: les.youtubeUrl,
          };

          const lessonResponse = await fetch(`${API_URL}/courses/${courseId}/modules/${moduleId}/lessons`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(lessonPayload),
          });

          if (!lessonResponse.ok) {
            const errorData = await lessonResponse.json().catch(() => ({}));
            throw new Error(errorData.message || 'Erro ao cadastrar aula no servidor.');
          }
        }
      }

      setSuccessMessage('Curso cadastrado com sucesso!');
      setName('');
      setDescription('');
      setModules([]);
      setAssessments([]);
      fetchCourses();
      if (onCreationSuccess) onCreationSuccess();
    } catch (error) {
      console.error('Request error:', error);
      setErrorMessage(error.message || 'Não foi possível conectar ao servidor backend.');
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <>
      <div className="course-container">
        <h3>Criar Novo Curso</h3>

        {successMessage && <div className="alert alert-success">{successMessage}</div>}
        {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Nome do Curso *</label>
            <input 
              type="text" 
              placeholder="Ex: Curso Completo de Java"
              value={name} 
              onChange={(e) => setName(e.target.value)} 
              maxLength={120} />
          </div>

          <div className="form-group">
            <label>Descrição *</label>
            <textarea 
            placeholder="Breve descrição do curso (máx. 255 caracteres)"
            value={description} 
            onChange={(e) => setDescription(e.target.value)} 
            maxLength={255} />
          </div>

          {/* SESSÃO DE MÓDULOS NO FORMULÁRIO */}
          <div className="modules-section">
            <h4>Módulos e Aulas</h4>
            {modules.map((mod, index) => (
              <div key={mod.id} className="module-creation-box">
                <input 
                  type="text" 
                  placeholder={`Título do Módulo ${index + 1}`} 
                  value={mod.title}
                  onChange={(e) => handleModuleTitleChange(mod.id, e.target.value)}
                  className="input-module-title"
                />
                
                {mod.lessons.map((lesson, lIndex) => (
                  <div key={lesson.id} className="lesson-creation-box">
                    <input 
                      type="text" 
                      placeholder={`Título da Aula ${lIndex + 1}`} 
                      value={lesson.title}
                      onChange={(e) => handleLessonChange(mod.id, lesson.id, 'title', e.target.value)}
                    />
                    <input 
                      type="text" 
                      placeholder="URL do YouTube" 
                      value={lesson.youtubeUrl}
                      onChange={(e) => handleLessonChange(mod.id, lesson.id, 'youtubeUrl', e.target.value)}
                    />
                  </div>
                ))}
                
                <button type="button" className="btn-add-lesson" onClick={() => handleAddLesson(mod.id)}>
                  + Adicionar Aula
                </button>
              </div>
            ))}
            
            <button type="button" className="btn-add-module" onClick={handleAddModule}>
              + Adicionar Módulo
            </button>
          </div>

          {/* BOTÃO PARA ABRIR MODAL DE AVALIAÇÃO */}
          <div className="form-group" style={{ marginTop: '20px' }}>
            <button type="button" className="btn-add-assessment" onClick={() => setIsAssessmentModalOpen(true)}>
              📝 Adicionar Avaliações ({assessments.length})
            </button>
          </div>

          <button type="submit" disabled={isLoading} className="btn-submit">
            {isLoading ? 'Cadastrando...' : 'Cadastrar Curso completo'}
          </button>
        </form>
      </div>

      {/* LISTAGEM DE CURSOS (COLLAPSIBLE) */}
      <div className="course-list">
        <hr />
        <h3>Lista de Cursos</h3>
        {courses.length === 0 ? (
          <p className="empty-list">Nenhum curso cadastrado ainda.</p>
        ) : (
          visibleCourses.map(curso => (
            <div key={curso.id} className="course-card">
              {/* Header do Curso (Clicável) */}
              <div className="collapsible-header course-header" onClick={() => toggleCourse(curso.id)}>
                <strong>{curso.name}</strong>
                <span className={`arrow ${expandedCourses[curso.id] ? 'open' : ''}`}>▼</span>
              </div>

              {/* Corpo do Curso (Expansível) */}
              {expandedCourses[curso.id] && (
                <div className="collapsible-body">
                  <p className="course-desc"><em>{curso.description}</em></p>
                    <button type="button" className="btn-delete-course" onClick={() => handleDeleteCourse(curso.id)}>
                      Excluir Curso
                    </button>
                    {curso.courseModules && curso.courseModules.length > 0 ? (
                      curso.courseModules.map(module => (
                        <div key={module.id} className="module-card">
                          
                          {/* Header do Módulo (Clicável) */}
                          <div className="collapsible-header module-header" onClick={() => toggleModule(module.id)}>
                            <span>📦 {module.title || module.name}</span>
                            <span className={`arrow ${expandedModules[module.id] ? 'open' : ''}`}>▼</span>
                          </div>
                          <button type="button" className="btn-delete-module" onClick={() => handleDeleteModule(curso.id, module.id)}>
                            Excluir Módulo
                          </button>

                          {/* Corpo do Módulo (Aulas e Miniaturas) */}
                          {expandedModules[module.id] && (
                            <div className="lessons-container">
                              {module.lessons && module.lessons.length > 0 ? (
                                module.lessons.map(lesson => {
                                  const thumb = getYouTubeThumbnail(lesson.videoUrl);
                                  return (
                                    <div key={lesson.id} className="lesson-item">
                                      {thumb ? (
                                        <img src={thumb} alt="Miniatura" className="youtube-thumb" />
                                      ) : (
                                        <div className="no-thumb">Sem vídeo</div>
                                      )}
                                      <div className="lesson-info">
                                        <span className="lesson-title">▶ {lesson.title}</span>
                                        {lesson.duration && (
                                          <small className="lesson-duration"> ({lesson.duration} min)</small>
                                        )}
                                      </div>
                                      <button type="button" className="btn-delete-lesson" onClick={() => handleDeleteLesson(curso.id, module.id, lesson.id)}>
                                        Excluir Aula
                                      </button>
                                    </div>
                                  )
                                })
                              ) : (
                                <p className="empty-lessons">Nenhuma aula neste módulo.</p>
                              )}
                            </div>
                          )}
                        </div>
                      ))
                    ) : (
                      <p className="empty-list">Nenhum módulo cadastrado neste curso.</p>
                    )}
                </div>
              )}
            </div>
          ))
        )}
        {courses.length > 3 && (
          <button type="button" className="btn-show-more" onClick={toggleShowAllCourses}>
            {showAllCourses ? 'Ver Menos' : 'Ver Mais'}
          </button>
        )}
      </div>

      {/* MODAL FLUTUANTE DE AVALIAÇÕES */}
      {isAssessmentModalOpen && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h3>Adicionar Avaliações</h3>
            
            <div className="assessment-list">
              {assessments.map((ass, index) => (
                <div key={ass.id} className="assessment-item">
                  <strong>Q{index + 1}:</strong> {ass.question}
                </div>
              ))}
            </div>

            <div className="add-question-box">
              <label>Nova Pergunta:</label>
              <input 
                type="text" 
                value={currentQuestion} 
                onChange={(e) => setCurrentQuestion(e.target.value)} 
                placeholder="Ex: O que é Spring Boot?"
              />
              <button type="button" onClick={handleAddAssessmentQuestion} className="btn-save-question">
                Salvar Pergunta
              </button>
            </div>

            <button type="button" onClick={() => setIsAssessmentModalOpen(false)} className="btn-close-modal">
              Concluir e Fechar
            </button>
          </div>
        </div>
      )}
    </>
  );
}

export default RegisterCourses;