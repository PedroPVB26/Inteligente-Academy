// Configuração da URL base da API usando variáveis de ambiente do Vite
export const API_URL = import.meta.env.VITE_API_URL;

// Função utilitária para centralizar cabeçalhos e tratamentos de erro comuns do fetch
export const handleRequest = async (url, options = {}) => {
  try {
    const response = await fetch(`${API_URL}${url}`, {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(errorData.message || `Erro na requisição: ${response.status}`);
    }

    // Se não houver conteúdo (ex: 204 No Content), retorna nulo de forma segura
    if (response.status === 204) return null;

    return await response.json();
  } catch (error) {
    console.error(`Erro na chamada da API (${url}):`, error);
    throw error;
  }
};