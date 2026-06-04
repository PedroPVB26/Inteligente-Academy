const API = import.meta.env.VITE_API_URL ?? "";

export async function listUsers() {

    const response = await fetch(`${API}/users`);

    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }

    return await response.json();
}

export async function createUser(user) {

    const response = await fetch(`${API}/users`, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(user)

    });

    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }

    return await response.json();
}