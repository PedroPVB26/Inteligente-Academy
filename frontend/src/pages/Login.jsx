import React, { useState } from 'react';
import '../styles/Login.css';

export default function Login({ onClose = () => {}, onOpenRegister = () => {} }) {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Tentando logar com:\nEmail: ${email}\nSenha: ${senha}`);
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
          <p>Junte-se a nós e comece a estudar agora. Prometemos manter seus dados seguros.</p>
        </div>

        {/* Formulário */}
        <form onSubmit={handleSubmit} className="login-form">
          
          {/* Campo Email */}
          <div className="input-group">
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="E-mail"
              required
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
            />
          </div>

          {/* Botão Login */}
          <button type="submit" className="btn-login-submit">
            Login
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