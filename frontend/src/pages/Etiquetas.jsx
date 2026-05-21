import { useEffect, useState } from "react";

const API = import.meta.env.VITE_API_URL ?? "";

function Etiquetas() {

  const [etiquetas, setEtiquetas] = useState([]);
  const [error, setError] = useState(null);

  const [nome, setNome] = useState("");


  // GET
  async function carregarEtiquetas() {

    try {

      const resposta = await fetch(`${API}/etiqueta`);

      if (!resposta.ok) {
        throw new Error(`HTTP ${resposta.status}`);
      }

      const dados = await resposta.json();

      setEtiquetas(dados);
      setError(null);

    } catch (erro) {
      console.log("Erro ao carregar etiquetas", erro);
      setError(erro.message || String(erro));
    }
  }


  useEffect(() => {
    console.log('API base URL:', API);
    carregarEtiquetas();
  }, []);


  // POST
  async function cadastrarEtiqueta() {

    if (!nome) {
      alert("Digite o nome da etiqueta");
      return;
    }

    try {

      const novaEtiqueta = {
        nome
      };

      const resposta = await fetch(`${API}/etiqueta`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(novaEtiqueta)
      });

      if (resposta.ok) {

        alert("Etiqueta cadastrada!");

        carregarEtiquetas();

        setNome("");

      } else {
        alert("Erro ao cadastrar etiqueta");
      }

    } catch (erro) {
      console.log("Erro no cadastro", erro);
    }
  }


  return (
    <div style={{ padding: "20px" }}>

      <h2>Etiquetas</h2>
      <p style={{fontSize:12, color:'#666'}}>API: {API}</p>
      {error && <p style={{color:'crimson'}}>Erro: {error}</p>}

      <input
        type="text"
        placeholder="Nome da etiqueta"
        value={nome}
        onChange={(e) => setNome(e.target.value)}
      />

      <br /><br />

      <button onClick={cadastrarEtiqueta}>
        Cadastrar
      </button>

      <hr />

      <h3>Lista de Etiquetas</h3>

      {
        etiquetas.map(etiqueta => (
          <div
            key={etiqueta.id}
            style={{
              border: "1px solid gray",
              padding: "10px",
              marginBottom: "10px"
            }}
          >

            <p>
              <strong>Nome:</strong> {etiqueta.nome}
            </p>

            <p>
              <strong>Criado em:</strong> {etiqueta.createdAt}
            </p>

            <p>
              <strong>Modificado em:</strong> {etiqueta.modifiedAt}
            </p>

          </div>
        ))
      }

    </div>
  );
}

export default Etiquetas;