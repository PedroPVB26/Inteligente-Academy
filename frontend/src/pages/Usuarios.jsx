import { useEffect, useState } from "react";

const API = import.meta.env.VITE_API_URL ?? "";

function Usuarios() {

  const [usuarios, setUsuarios] = useState([]);
  const [error, setError] = useState(null);

  const [cpf, setCpf] = useState("");
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [dataNascimento, setDataNascimento] = useState("");
  const [tipoUsuario, setTipoUsuario] = useState("ALUNO");
  const [senha, setSenha] = useState("");


  // GET
  async function carregarUsuarios() {

    try {

      const resposta = await fetch(`${API}/usuario`);

      if (!resposta.ok) {
        throw new Error(`HTTP ${resposta.status}`);
      }

      const dados = await resposta.json();

      setUsuarios(dados);
      setError(null);

    } catch (erro) {
      console.log("Erro ao carregar usuários", erro);
      setError(erro.message || String(erro));
    }
  }


  useEffect(() => {
    console.log('API base URL:', API);
    carregarUsuarios();
  }, []);


  // POST
  async function cadastrarUsuario() {

    if (!cpf || !nome || !email || !dataNascimento || !senha) {
      alert("Preencha todos os campos");
      return;
    }

    try {

      const novoUsuario = {
        cpf,
        nome,
        email,
        dataNascimento,
        verificado: false,
        tipoUsuario,
        senha
      };

      const resposta = await fetch(`${API}/usuario`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(novoUsuario)
      });

      if (resposta.ok) {

        alert("Usuário cadastrado!");

        carregarUsuarios();

        setCpf("");
        setNome("");
        setEmail("");
        setDataNascimento("");
        setTipoUsuario("ALUNO");

      } else {
        alert("Erro ao cadastrar usuário");
      }

    } catch (erro) {
      console.log("Erro no cadastro", erro);
    }
  }


  return (
    <div style={{ padding: "20px" }}>

      <h2>Usuários</h2>
      <p style={{fontSize:12, color:'#666'}}>API: {API}</p>
      {error && <p style={{color:'crimson'}}>Erro: {error}</p>}

      <input
        type="text"
        placeholder="CPF"
        value={cpf}
        onChange={(e) => setCpf(e.target.value)}
      />

      <br /><br />

      <input
        type="text"
        placeholder="Nome"
        value={nome}
        onChange={(e) => setNome(e.target.value)}
      />

      <br /><br />

      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <br /><br />

      <input
        type="password"
        placeholder="Senha"
        value={senha}
        onChange={(e) => setSenha(e.target.value)}
      />

      <br /><br />

      <input
        type="date"
        value={dataNascimento}
        onChange={(e) => setDataNascimento(e.target.value)}
      />

      <br /><br />

      <select
        value={tipoUsuario}
        onChange={(e) => setTipoUsuario(e.target.value)}
      >
        <option value="ALUNO">ALUNO</option>
        <option value="PROFESSOR">PROFESSOR</option>
        <option value="ADMIN">ADMIN</option>
      </select>

      <br /><br />

      <button onClick={cadastrarUsuario}>
        Cadastrar
      </button>

      <hr />

      <h3>Lista de Usuários</h3>

      {
        usuarios.map(usuario => (
          <div
            key={usuario.id}
            style={{
              border: "1px solid gray",
              padding: "10px",
              marginBottom: "10px"
            }}
          >

            <p>
              <strong>Nome:</strong> {usuario.nome}
            </p>

            <p>
              <strong>CPF:</strong> {usuario.cpf}
            </p>

            <p>
              <strong>Email:</strong> {usuario.email}
            </p>

            <p>
              <strong>Tipo:</strong> {usuario.tipoUsuario}
            </p>

            <p>
              <strong>Criado em:</strong> {usuario.createdAt}
            </p>

            <p>
              <strong>Modificado em:</strong> {usuario.modifiedAt}
            </p>

            <p>
              <strong>Verificado:</strong>
              {" "}
              {usuario.verificado ? "Sim" : "Não"}
            </p>

            

          </div>
        ))
      }

    </div>
  );
}

export default Usuarios;