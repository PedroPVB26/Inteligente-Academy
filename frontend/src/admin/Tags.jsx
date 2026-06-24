import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";

const API = import.meta.env.VITE_API_URL ?? "";

function Tags() {
  const { t } = useTranslation();
  const [tags, setTags] = useState([]);
  const [error, setError] = useState(null);

  const [name, setName] = useState("");

// 1. Função utilitária para buscar o token (ajuste a chave "token" se usar outro nome no login)
  const getToken = () => localStorage.getItem("accessToken");

  // GET atualizado
  async function loadTags() {
    try {
      const token = getToken(); // Busca o token
      if (!token) {
        token = localStorage.getItem("refreshToken"); // Tenta buscar o refresh token se o access token não estiver disponível
        return;
      }
      const response = await fetch(`${API}/tags`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          // Envia o crachá de identificação para o Spring Boot
          "Authorization": `Bearer ${token}` 
        }
      });

      if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
      }

      const dados = await response.json();
      setTags(dados);
      setError(null);
    } catch (error) {
      console.log("Erro ao carregar Tags", error);
      setError(error.message || String(error));
    }
  }

  // POST atualizado
  async function registerTag() {
    if (!name) {
      alert("Digite o name da tag");
      return;
    }

    try {
      const newTag = { name };
      const token = getToken(); // Busca o token

      const response = await fetch(`${API}/tags`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // Envia o token para demonstrar que tem permissão de criar
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(newTag)
      });

      if (response.ok) {
        alert("Tag cadastrada!");
        loadTags();
        setName("");
      } else {
        alert("Erro ao cadastrar tag");
      }
    } catch (erro) {
      console.log("Erro no cadastro", erro);
    }
  }

  return (
    <div style={{ padding: "20px" }}>

      <h2>Tags</h2>
      <p style={{fontSize:12, color:'#666'}}>API: {API}</p>
      {error && <p style={{color:'crimson'}}>Erro: {error}</p>}

      <input
        type="text"
        placeholder={t("tags.placeholder")}
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <br /><br />

      <button onClick={registerTag}>
        Cadastrar
      </button>

      <hr />

      <h3>Lista de Tags</h3>

      {
        tags.map(tag => (
          <div
            key={tag.id}
            style={{
              border: "1px solid gray",
              padding: "10px",
              marginBottom: "10px"
            }}
          >

            <p>
              <strong>name:</strong> {tag.name}
            </p>

            <p>
              <strong>Criado em:</strong> {tag.createdAt}
            </p>

            <p>
              <strong>Modificado em:</strong> {tag.modifiedAt}
            </p>

          </div>
        ))
      }

    </div>
  );
}

export default Tags;