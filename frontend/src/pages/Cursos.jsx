import { useEffect, useState } from "react";

const API = import.meta.env.VITE_API_URL ?? "";

function Cursos() {

  const [cursos, setCursos] = useState([]);
  const [error, setError] = useState(null);

  const [nome, setNome] = useState("");
  const [descricao, setDescricao] = useState("");
  const [duracao, setDuracao] = useState("");


  // GET
  async function carregarCursos() {

    try {

      const resposta = await fetch(`${API}/curso`);

      if (!resposta.ok) {
        throw new Error(`HTTP ${resposta.status}`);
      }

      const dados = await resposta.json();

      setCursos(dados);
      setError(null);

    } catch (erro) {
      console.log("Erro ao carregar cursos", erro);
      setError(erro.message || String(erro));
    }
  }


  useEffect(() => {
    console.log('API base URL:', API);
    carregarCursos();
  }, []);


  // POST
  async function cadastrarCurso() {

    if (!nome || !descricao || !duracao) {
      alert("Preencha todos os campos");
      return;
    }

    try {

      const novoCurso = {
        nome,
        descricao,
        duracao: Number(duracao)
      };

      const resposta = await fetch(`${API}/curso`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(novoCurso)
      });

      if (resposta.ok) {

        alert("Curso cadastrado!");

        carregarCursos();

        setNome("");
        setDescricao("");
        setDuracao("");

      } else {
        alert("Erro ao cadastrar curso");
      }

    } catch (erro) {
      console.log("Erro no cadastro", erro);
    }
  }


  return (
    <div style={{ padding: "20px" }}>

      <h2>Cursos</h2>
      <p style={{fontSize:12, color:'#666'}}>API: {API}</p>
      {error && <p style={{color:'crimson'}}>Erro: {error}</p>}

      <input
        type="text"
        placeholder="Nome"
        value={nome}
        onChange={(e) => setNome(e.target.value)}
      />

      <br /><br />

      <input
        type="text"
        placeholder="Descrição"
        value={descricao}
        onChange={(e) => setDescricao(e.target.value)}
      />

      <br /><br />

      <input
        type="number"
        placeholder="Duração"
        value={duracao}
        onChange={(e) => setDuracao(e.target.value)}
      />

      <br /><br />

      <button onClick={cadastrarCurso}>
        Cadastrar
      </button>

      <hr />

      <h3>Lista de Cursos</h3>

      {
        cursos.map(curso => (
          <div
            key={curso.id}
            style={{
              border: "1px solid gray",
              padding: "10px",
              marginBottom: "10px"
            }}
          >

            <p>
              <strong>Nome:</strong> {curso.nome}
            </p>

            <p>
              <strong>Descrição:</strong> {curso.descricao}
            </p>

            <p>
              <strong>Duração:</strong> {curso.duracao}
            </p>

            <p>
              <strong>Criado em:</strong> {curso.createdAt}
            </p>

            <p>
              <strong>Modificado em:</strong> {curso.modifiedAt}
            </p>

          </div>
        ))
      }

    </div>
  );
}

export default Cursos;