import React, { useState } from 'react';

export default function Login() {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    alert(`Tentando logar com:\nEmail: ${email}\nSenha: ${senha}`);
  };

  return (
    <div>
      {/* Botão Fechar */}
      <button onClick={() => alert('Fechar clicado')}>
        X
      </button>

      {/* Cabeçalho */}
      <div>
        <span>🎓</span>
        <h2>InteliGente Academy</h2>
        <p>Junte-se a nós e comece a estudar agora. Prometemos manter seus dados seguros.</p>
      </div>

      {/* Formulário */}
      <form onSubmit={handleSubmit}>
        
        {/* Campo Email */}
        <div>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="E-mail"
            required
          />
        </div>

        {/* Campo Senha */}
        <div>
          <input
            type="password"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            placeholder="Senha"
            required
          />
        </div>

        {/* Botão Login */}
        <button type="submit">
          Login
        </button>
      </form>

      <p>ou</p>

      {/* Botão Google */}
      <button type="button" onClick={() => alert('Login com Google')}>
        Entrar com Google
      </button>

      {/* Rodapé */}
      <p>
        Ainda não tem uma conta?{' '}
        <a href="#inscrever">Inscrever-se</a>
      </p>
    </div>
  );
}