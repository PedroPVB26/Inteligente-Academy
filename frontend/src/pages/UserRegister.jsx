import { useEffect, useState } from "react";

const API = import.meta.env.VITE_API_URL ?? "";

function UserRegister() {
    const {t} = useTranslation();

    const [cpf, setCpf] = useState([]);
    const [name, setName] = useState("");
    const [email, setEmail] = useState([]);
    const [password, setPassword] = useState("");
    const [birthDate, setBirthDate] = useState([]);
    const [userType, setUserType] = useState("");

    const [error, setError] = useState(null);

    // GET
    async function loadUsers() {
        try{
            const response = await fetch(`${API}/users`);

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }
            const data = await response.json();
            setCpf(data.map(user => user.cpf));
            setName(data.map(user => user.name));
            setEmail(data.map(user => user.email));
            setPassword(data.map(user => user.password));
            setBirthDate(data.map(user => user.birthDate));
            setUserType(data.map(user => user.userType));

            setError(null);
        } catch (error) {
            console.log("Erro ao carregar usuários", error);
            setError(error.message || String(error));
        }
    }

    useEffect(() => {
        console.log('API base URL:', API);
        loadUsers();
    }, []);

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
                userType
            };
            const response = await fetch(`${API}/users`, {
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
        <div>
            <h1>{t('userRegister.title')}</h1>
            {/* Formulário de registro de usuário */}
            <input type="text" placeholder="CPF" value={cpf} onChange={e => setCpf(e.target.value)} />
            <input type="text" placeholder="Nome" value={name} onChange={e => setName(e.target.value)} />
            <input type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
            <input type="password" placeholder="Senha" value={password} onChange={e => setPassword(e.target.value)} />
            <input type="date" placeholder="Data de Nascimento" value={birthDate} onChange={e => setBirthDate(e.target.value)} />
            <select value={userType} onChange={e => setUserType(e.target.value)}>
                <option value="">Selecione o tipo de usuário</option>
                <option value="client">Cliente</option>
                <option value="admin">Administrador</option>
            </select>
            <button onClick={registerUser}>Cadastrar</button>
        </div>
    );
}

export default UserRegister;