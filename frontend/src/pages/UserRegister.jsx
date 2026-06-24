import { useState } from "react";
import "../styles/UserRegister.css";

const API = import.meta.env.VITE_API_URL ?? "";

function formatCpf(value) {
    const digits = value.replace(/\D/g, "").slice(0, 11);

    return digits
        .replace(/(\d{3})(\d)/, "$1.$2")
        .replace(/(\d{3})(\d)/, "$1.$2")
        .replace(/(\d{3})(\d{1,2})$/, "$1-$2");
}

function UserRegister({ onClose = () => {} }) {
    const [cpf, setCpf] = useState("");
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const userRole = "ALUNO";

    // POST
    async function registerUser() {
        if (!cpf || !name || !email || !password || !birthDate) {
            alert("Preencha todos os campos");
            return;
        }
        try {
            const newUser = {
                cpf,
                name,
                email,
                password,
                birthDate,
                userRole
            };
            const response = await fetch(`${API}/auth/register`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(newUser)
            });

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }

            const createdUser = await response.json();
            console.log("Usuário criado:", createdUser);
        } catch (error) {
            console.log("Erro no cadastro", error);
        }
    }

    return (
        <div className="login-page-overlay">
            <div className="user-register-card login-card-container">
                <button
                    type="button"
                    onClick={onClose}
                    aria-label="Fechar cadastro"
                    className="login-close-button"
                >
                    ×
                </button>

                <h1 className="user-register-title">Cadastro de Usuário</h1>

                <div className="user-register-form">
                    {/* Formulário de registro de usuário */}
                    <input
                        className="user-register-field"
                        type="text"
                        inputMode="numeric"
                        placeholder="CPF"
                        value={formatCpf(cpf)}
                        onChange={e => setCpf(e.target.value.replace(/\D/g, "").slice(0, 11))}
                    />
                    <input className="user-register-field" type="text" placeholder="Nome" value={name} onChange={e => setName(e.target.value)} />
                    <input className="user-register-field" type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
                    <input className="user-register-field" type="password" placeholder="Senha" value={password} onChange={e => setPassword(e.target.value)} />
                    <input className="user-register-field" type="date" placeholder="Data de Nascimento" value={birthDate} onChange={e => setBirthDate(e.target.value)} />
                    <button type="button" className="user-register-submit" onClick={registerUser}>Cadastrar</button>
                </div>
            </div>
        </div>
    );
}

export default UserRegister;