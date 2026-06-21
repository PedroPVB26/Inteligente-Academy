export default function DetailsCourses() {
    return (
        <main>
        {allCertificates.map((certificado) => (
          <section key={certificado.id}>
            <div>
              <span>[Ícone Medalha]</span>
              <h2>Certificado de conclusão</h2>
            </div>

            <ul>
              <li>
                <strong>Curso:</strong> {certificado.curso}
              </li>
              <li>
                <strong>Horas:</strong> {certificado.horas}
              </li>
              <li>
                <strong>Data de emissão:</strong> {certificado.dataEmissao}
              </li>
              <li>
                <strong>Código:</strong> {certificado.codigo}
              </li>
            </ul>

            {/* Botões de Ação passando o contexto do certificado atual */}
            <div>
              <button 
                type="button" 
                onClick={() => handleViewCertificate(certificado.id, certificado.curso)}
              >
                Visualizar certificado
              </button>
              <button 
                type="button" 
                onClick={() => handleDownloadCertificate(certificado.id, certificado.curso)}
              >
                Baixar certificado
              </button>
            </div>
          </section>
        ))}
      </main>
    );
}