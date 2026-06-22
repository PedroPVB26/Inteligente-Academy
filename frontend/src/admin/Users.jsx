import { useEffect, useState } from "react";

import "../styles/users.css";


import {
    listUsers,
    createUser
} from "../services/userService";

function formatCpf(value) {

    const digits = value.replace(/\D/g, "").slice(0, 11);

    return digits
        .replace(/(\d{3})(\d)/, "$1.$2")
        .replace(/(\d{3})(\d)/, "$1.$2")
        .replace(/(\d{3})(\d{1,2})$/, "$1-$2");

}

function UserCard({ user }) {
    if (!user) {
        return null;
    }

    return (

        <div className="user-card">

            <p>
                <strong>Nome:</strong> {user.name}
            </p>

            <p>
                <strong>CPF:</strong> {user.cpf}
            </p>

            <p>
                <strong>Email:</strong> {user.email}
            </p>

            <p>
                <strong>Data de Nascimento:</strong> {user.birthDate}
            </p>

            <p>
                <strong>Email:</strong> {user.email}
            </p>

            <p>
                <strong>Tipo:</strong> {user.userRole}
            </p>

            <p>
                <strong>Criado em:</strong> {user.createdAt}
            </p>

            <p>
                <strong>Modificado em:</strong> {user.modifiedAt}
            </p>

            <p>
                <strong>Verificado:</strong>{" "}
                {user.verified ? "Sim" : "Não"}
            </p>

        </div>

    );

}

function Users() {

    const [users, setUsers] = useState([]);
    const [error, setError] = useState(null);

    const [cpf, setCpf] = useState("");
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [userRole, setUserRole] = useState("ALUNO");


    async function load() {

        try {

            const dados = await listUsers();

            setUsers(dados);

            setError(null);

        } catch (erro) {

            console.log(erro);

            setError(erro.message);

        }
    }


    useEffect(() => {

        load();

    }, []);


    async function registerUser() {

        if (
            !cpf ||
            !name ||
            !email ||
            !password ||
            !birthDate
        ) {

            alert("Preencha todos os campos");
            return;
        }

        try {

            await createUser({

                cpf,
                name,
                email,
                password,
                birthDate,
                userRole,

            });

            load();

            setCpf("");
            setName("");
            setEmail("");
            setPassword("");
            setBirthDate("");
            setUserRole("ALUNO");

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
                    inputMode="numeric"
                    value={formatCpf(cpf)}
                    onChange={(e) => setCpf(e.target.value.replace(/\D/g, "").slice(0, 11))}
                />

                <input
                    type="text"
                    placeholder="Nome"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
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
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                <input
                    type="date"
                    value={birthDate}
                    onChange={(e) => setBirthDate(e.target.value)}
                />

                <select
                    value={userRole}
                    onChange={(e) => setUserRole(e.target.value)}
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
                    onClick={registerUser}
                >
                    Cadastrar
                </button>

            </div>

            <hr />

            <h3>Lista de Usuários</h3>

            {

                users.map(usuario => (

                    <UserCard
                        key={usuario.id}
                        user={usuario}
                    />

                ))

            }

        </div>

    );

}

export default Users;