const API = import.meta.env.VITE_API_URL ?? "";

export async function listarUsuarios() {

    const resposta = await fetch(`${API}/usuario`);

    if (!resposta.ok) {
        throw new Error(`HTTP ${resposta.status}`);
    }

    return await resposta.json();
}

export async function criarUsuario(usuario) {

    const resposta = await fetch(`${API}/usuario`, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(usuario)

    });

    if (!resposta.ok) {
        throw new Error(`HTTP ${resposta.status}`);
    }

    return await resposta.json();
}