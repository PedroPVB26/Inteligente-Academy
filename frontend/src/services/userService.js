import { getAccessToken } from "./authService"; // Importante: ajuste o caminho "./authService" se ele estiver em outra pasta

const API = import.meta.env.VITE_API_URL ?? "";

// Função auxiliar para injetar o token nos cabeçalhos
const getAuthHeaders = () => {
    const headers = { "Content-Type": "application/json" };
    const token = getAccessToken();
    
    if (token) {
        headers["Authorization"] = `Bearer ${token}`;
    }
    
    return headers;
};

export async function listUsers() {
    const response = await fetch(`${API}/users`, {
        method: "GET",
        headers: getAuthHeaders(),
    });

    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }

    return await response.json();
}

export async function login(email, password) {
    // Login NÃO recebe token, pois é aqui que ele é gerado!
    const response = await fetch(`${API}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ email, password })
    });

    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.message || `HTTP ${response.status}`);
    }

    return await response.json();
}

export async function createUser(user) {
    const response = await fetch(`${API}/auth/register`, {
        method: "POST",
        headers: getAuthHeaders(), // <-- Injetando o token aqui
        body: JSON.stringify(user)
    });

    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }

    return await response.json();
}