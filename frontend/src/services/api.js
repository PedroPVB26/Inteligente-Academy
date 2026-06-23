// Configuração da URL base da API usando variáveis de ambiente do Vite.
// Quando a variável não existir, usa a origem atual do navegador.
export const API_URL = import.meta.env.VITE_API_URL ?? window.location.origin;

// Função utilitária para centralizar cabeçalhos e tratamentos de erro comuns do fetch
export const handleRequest = async (url, options = {}) => {
  try {
    const response = await fetch(new URL(url, API_URL), {
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