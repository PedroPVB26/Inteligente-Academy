import React from 'react';
import '../styles/Certificates.css';

export default function DetailsCourses({ allCertificates = [] }) {
  
  // Funções dummy caso ainda não tenha as originais mapeadas no componente:
  const handleViewCertificate = (id, curso) => console.log("Visualizar", id, curso);
  const handleDownloadCertificate = (id, curso) => console.log("Baixar", id, curso);

  return (
    <main className="certificates-page-container">
      <header className="certificates-header">
        <h1 className="certificates-title">
          Meus <span className="highlight-orange">Certificados</span>
        </h1>
        <p className="certificates-subtitle">
          Gerencie e faça o download de suas conquistas na InteliGente Academy
        </p>
      </header>

      {/* Grid de Certificados */}
      <div className="certificates-grid">
        {allCertificates.length > 0 ? (
          allCertificates.map((certificado) => (
            <section className="certificate-card" key={certificado.id}>
              
              {/* Cabeçalho do Card */}
              <div className="card-cert-header">
                <div className="medal-icon-box">🏅</div>
                <div className="card-cert-title-group">
                  <span className="badge-conclusao">Concluído</span>
                  <h2>Certificado de Conclusão</h2>
                </div>
              </div>

              {/* Corpo de Informações */}
              <div className="card-cert-body">
                <h3 className="cert-course-name">{certificado.curso}</h3>
                
                <div className="cert-meta-grid">
                  <div className="meta-item">
                    <span className="meta-label">Carga Horária:</span>
                    <span className="meta-value">{certificado.horas}</span>
                  </div>
                  <div className="meta-item">
                    <span className="meta-label">Emissão:</span>
                    <span className="meta-value">{certificado.dataEmissao}</span>
                  </div>
                </div>

                <div className="cert-code-box">
                  <span className="code-label">CÓDIGO DE VALIDAÇÃO</span>
                  <code className="code-value">{certificado.codigo}</code>
                </div>
              </div>

              {/* Botões de Ação */}
              <div className="card-cert-actions">
                <button 
                  type="button" 
                  className="btn-cert-secondary"
                  onClick={() => handleViewCertificate(certificado.id, certificado.curso)}
                >
                  <span className="btn-icon">👁️</span> Visualizar
                </button>
                
                <button 
                  type="button" 
                  className="btn-cert-primary"
                  onClick={() => handleDownloadCertificate(certificado.id, certificado.curso)}
                >
                  <span className="btn-icon">⬇️</span> Baixar PDF
                </button>
              </div>

            </section>
          ))
        ) : (
          <div className="no-certificates">
            <span className="empty-icon">📜</span>
            <p>Você ainda não possui certificados emitidos.</p>
          </div>
        )}
      </div>
    </main>
  );
}