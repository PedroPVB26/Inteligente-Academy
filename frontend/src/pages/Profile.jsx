export default function Profile() {
  return (
    <div>
      <aside>
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

      <main>
        <h1>Perfil público</h1>
        
        {activeTab === 'perfil' ? (
          <form onSubmit={(e) => e.preventDefault()}>
            <h3>Dados básicos:</h3>
            
            <div>
              <input
                type="text"
                name="nome"
                value={formData.nome}
                onChange={handleChange}
                placeholder="Nome"
              />
            </div>
            
            <div>
              <input
                type="text"
                name="sobrenome"
                value={formData.sobrenome}
                onChange={handleChange}
                placeholder="Sobrenome"
              />
            </div>
            
            <div>
              <input
                type="text"
                name="profissao"
                value={formData.profissao}
                onChange={handleChange}
                placeholder="Profissão"
              />
            </div>
          </form>
        ) : (
          <div>
            <h3>Segurança da conta</h3>
            <p>Conteúdo de segurança da conta aqui.</p>
          </div>
        )}
      </main>
    </div>
  );
}