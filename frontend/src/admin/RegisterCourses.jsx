import { useState, useEffect } from 'react';
import './RegisterCourses.css'; // Importa o arquivo de estilos

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

function RegisterCourses({ onCreationSuccess }) {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  
  const [courses, setCourses] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetchCourses();
  }, []);

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

  async function handleSubmit(event) {
    event.preventDefault();

    // Removida a validação de duração já que o input foi retirado
    if (!name.trim() || !description.trim()) {
      setErrorMessage('Por favor, preencha todos os campos obrigatórios.');
      return;
    }

    setIsLoading(true);
    setErrorMessage('');
    setSuccessMessage('');

    const newCourse = {
      name,
      description
    };

    try {
      const response = await fetch(`${API_URL}/courses`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newCourse),
      });

      if (response.status === 201 || response.ok) {
        setSuccessMessage('Curso cadastrado com sucesso!');
        setName('');
        setDescription('');
        fetchCourses();
        if (onCreationSuccess) onCreationSuccess();
      } else {
        const errorData = await response.json().catch(() => ({}));
        setErrorMessage(errorData.message || 'Erro ao cadastrar curso no servidor.');
      }
    } catch (error) {
      console.error('Request error:', error);
      setErrorMessage('Não foi possível conectar ao servidor backend.');
    } finally {
      setIsLoading(false);
    }
  }

  return (
    // Resolvido o erro usando um React Fragment (<> e </>) para envelopar os dois blocos principais
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
              placeholder="Ex: Spring Boot com React" 
              value={name} 
              onChange={(e) => setName(e.target.value)} 
              maxLength={120} 
            />
          </div>

          <div className="form-group">
            <label>Descrição *</label>
            <textarea 
              placeholder="Forneça uma breve descrição..." 
              value={description} 
              onChange={(e) => setDescription(e.target.value)} 
              maxLength={255} 
            />
          </div>

          <div className="form-group">
            <label>Nome do Curso *</label>
            <input 
              type="text" 
              placeholder="Ex: Spring Boot com React" 
              value={name} 
              onChange={(e) => setName(e.target.value)} 
              maxLength={120} 
            />
          </div>

          <button type="submit" disabled={isLoading} className="btn-submit">
            {isLoading ? 'Cadastrando...' : 'Cadastrar Curso'}
          </button>
        </form>
      </div>

      <div className="course-list">
        <hr />
        <br />
        <h3>Lista de Cursos</h3>
        {courses.length === 0 ? (
          <p className="empty-list">Nenhum curso cadastrado ainda.</p>
        ) : (
          courses.map(curso => (
            <div key={curso.id} className="course-card">
              <p><strong>Nome:</strong> {curso.name}</p>
              <p><strong>Descrição:</strong> {curso.description}</p>
              <p><strong>Duração:</strong> {curso.duration} minutos</p>
            </div>
          ))
        )}
      </div>
    </>
  );
}

export default RegisterCourses;
