import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { handleRequest } from "../services/api";

function Tags() {
  const { t } = useTranslation();
  const [tags, setTags] = useState([]);
  const [error, setError] = useState(null);

  const [name, setName] = useState("");


  // GET
  async function loadTags() {

    try {

      const dados = await handleRequest('/tags');

      setTags(dados);
      setError(null);

    } catch (error) {
      console.log("Erro ao carregar Tags", error);
      setError(error.message || String(error));
    }
  }


  useEffect(() => {
    loadTags();
  }, []);


  // POST
  async function registerTag() {

    if (!name) {
      alert("Digite o name da tag");
      return;
    }

    try {

      const newTag = {
        name
      };

      await handleRequest('/tags', {
        method: 'POST',
        body: JSON.stringify(newTag)
      });

        alert("Tag cadastrada!");

        loadTags();

        setName("");

    } catch (erro) {
      console.log("Erro no cadastro", erro);
      alert("Erro ao cadastrar tag");
    }
  }


  return (
    <div style={{ padding: "20px" }}>

      <h2>Tags</h2>
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