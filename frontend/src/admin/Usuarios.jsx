import { useEffect, useState } from "react";

import "../styles/usuarios.css";

import UserCard from "../components/UserCard";

import {
    listarUsuarios,
    criarUsuario
} from "../services/usuarioService";

function Usuarios() {

    const [usuarios, setUsuarios] = useState([]);
    const [error, setError] = useState(null);

    const [cpf, setCpf] = useState("");
    const [nome, setNome] = useState("");
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [dataNascimento, setDataNascimento] = useState("");
    const [tipoUsuario, setTipoUsuario] = useState("ALUNO");


    async function carregar() {

        try {

            const dados = await listarUsuarios();

            setUsuarios(dados);

            setError(null);

        } catch (erro) {

            console.log(erro);

            setError(erro.message);

        }
    }


    useEffect(() => {

        carregar();

    }, []);


    async function cadastrarUsuario() {

        if (
            !cpf ||
            !nome ||
            !email ||
            !senha ||
            !dataNascimento
        ) {

            alert("Preencha todos os campos");
            return;
        }

        try {

            await criarUsuario({

                cpf,
                nome,
                email,
                senha,
                dataNascimento,
                tipoUsuario,

            });

            carregar();

            setCpf("");
            setNome("");
            setEmail("");
            setSenha("");
            setDataNascimento("");
            setTipoUsuario("ALUNO");

            alert("Usuário cadastrado!");

        } catch (erro) {

            console.log(erro);
            
            alert("Erro ao cadastrar usuário");

        }
    }


    return (

        <div className="container">

            <h2>Usuários</h2>

            <p className="api">
                API: {import.meta.env.VITE_API_URL}
            </p>

            {error &&
                <p className="error">
                    Erro: {error}
                </p>
            }

            <div className="formulario">

                <input
                    type="text"
                    placeholder="CPF"
                    value={cpf}
                    onChange={(e) => setCpf(e.target.value)}
                />

                <input
                    type="text"
                    placeholder="Nome"
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                />

                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Senha"
                    value={senha}
                    onChange={(e) => setSenha(e.target.value)}
                />

                <input
                    type="date"
                    value={dataNascimento}
                    onChange={(e) => setDataNascimento(e.target.value)}
                />

                <select
                    value={tipoUsuario}
                    onChange={(e) => setTipoUsuario(e.target.value)}
                >

                    <option value="ALUNO">
                        ALUNO
                    </option>

                    <option value="EDUCADOR">
                        EDUCADOR
                    </option>

                    <option value="ADMIN">
                        ADMIN
                    </option>

                </select>

                <button
                    onClick={cadastrarUsuario}
                >
                    Cadastrar
                </button>

            </div>

            <hr />

            <h3>Lista de Usuários</h3>

            {

                usuarios.map(usuario => (

                    <UserCard
                        key={usuario.id}
                        usuario={usuario}
                    />

                ))

            }

        </div>

    );

}

export default Usuarios;