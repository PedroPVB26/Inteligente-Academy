import React, { useState } from 'react';

export default function SecurityScreen() {
  return (
    <div>
      {/* Barra Lateral */}
      <aside>
        {/* Espaço reservado para a foto do usuário */}
        <div>[Foto do Usuário]</div>
        <h2>Nome do usuário</h2>
        
        <nav>
          <button onClick={() => setActiveTab('perfil')}>
            Perfil
          </button>
          <button onClick={() => setActiveTab('seguranca')}>
            Segurança da conta
          </button>
        </nav>
      </aside>

      {/* Conteúdo Principal */}
      <main>
        <h1>Segurança</h1>
        
        {activeTab === 'seguranca' ? (
          <form onSubmit={handlePasswordChangeSubmit}>
            
            {/* Campo: E-mail (possui um ícone/quadrado à direita no layout original) */}
            <div>
              <input
                type="email"
                name="email"
                value={securityData.email}
                onChange={handleChange}
                placeholder="E-mail"
              />
              {/* Representação do ícone de verificação/status ao lado do input */}
              <span>[ Ícone ]</span>
            </div>
            
            {/* Campo: Número de telefone */}
            <div>
              <input
                type="tel"
                name="telefone"
                value={securityData.telefone}
                onChange={handleChange}
                placeholder="Número de telefone"
              />
            </div>

            <h3>Alterar senha:</h3>
            
            {/* Campo: Senha atual */}
            <div>
              <input
                type="password"
                name="senhaAtual"
                value={securityData.senhaAtual}
                onChange={handleChange}
                placeholder="Senha atual"
              />
            </div>
            
            {/* Campo: Nova senha */}
            <div>
              <input
                type="password"
                name="novaSenha"
                value={securityData.novaSenha}
                onChange={handleChange}
                placeholder="Nova senha"
              />
            </div>
            
            {/* Campo: Confirmar nova senha */}
            <div>
              <input
                type="password"
                name="confirmarNovaSenha"
                value={securityData.confirmarNovaSenha}
                onChange={handleChange}
                placeholder="Confirmar nova senha"
              />
            </div>

            {/* Botão para Alterar Senha */}
            <button type="submit">
              Alterar senha
            </button>

          </form>
        ) : (
          <div>
            <h3>Perfil público</h3>
            <p>Conteúdo de perfil aqui.</p>
          </div>
        )}
      </main>
    </div>
  );
}