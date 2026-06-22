import React, { useState } from 'react';
import '../styles/Login.css';
import { login } from '../services/userService';
import { saveAuth } from '../services/authService';

export default function Login({ onClose = () => {}, onOpenRegister = () => {}, onLoginSuccess = () => {} }) {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await login(email, senha);
      saveAuth(response.accessToken, response.refreshToken);
      setEmail('');
      setSenha('');
      onLoginSuccess();
    } catch (err) {
      setError(err.message || 'Erro ao fazer login. Verifique suas credenciais.');
      console.error('Erro no login:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page-overlay">
      <div className="login-card-container">
        <button
          type="button"
          className="login-close-button"
          onClick={onClose}
          aria-label="Fechar login"
        >
          ×
        </button>

        {/* Cabeçalho */}
        <div className="login-header">
          <span className="login-logo-icon">🎓</span>
          <h2>InteliGente <span className="highlight-orange">Academy</span></h2>
          <p>Junte-se a nós e comece a estudar agora.</p>
          <p>Prometemos manter seus dados seguros.</p>
        </div>

        {/* Formulário */}
        <form onSubmit={handleSubmit} className="login-form">
          
          {/* Mensagem de Erro */}
          {error && <div className="login-error-message">{error}</div>}
          
          {/* Campo Email */}
          <div className="input-group">
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="E-mail"
              required
              disabled={loading}
            />
          </div>

          {/* Campo Senha */}
          <div className="input-group">
            <input
              type="password"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              placeholder="Senha"
              required
              disabled={loading}
            />
          </div>

          {/* Botão Login */}
          <button type="submit" className="btn-login-submit" disabled={loading}>
            {loading ? 'Fazendo login...' : 'Login'}
          </button>
        </form>

        <p className="login-divider">ou</p>

        {/* Botão Google */}
        <button type="button" className="btn-login-google" onClick={() => alert('Login com Google')}>
          <span className="google-icon">🌐</span> Entrar com Google
        </button>

        {/* Rodapé */}
        <p className="login-footer-text">
          Ainda não tem uma conta?{' '}
          <button type="button" className="highlight-orange login-footer-action" onClick={onOpenRegister}>
            Inscrever-se
          </button>
        </p>
      </div>
    </div>
  );
}