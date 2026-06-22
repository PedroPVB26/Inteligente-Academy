const ACCESS_TOKEN_KEY = 'accessToken';
const REFRESH_TOKEN_KEY = 'refreshToken';

const decodeBase64Url = (value) => {
  try {
    const base64 = value.replace(/-/g, '+').replace(/_/g, '/');
    const decoded = atob(base64);
    return decodeURIComponent(
      decoded
        .split('')
        .map((c) => `%${(`00${c.charCodeAt(0).toString(16)}`).slice(-2)}`)
        .join('')
    );
  } catch {
    return null;
  }
};

export const parseJwt = (token) => {
  if (!token) return null;
  const parts = token.split('.');
  if (parts.length !== 3) return null;

  const payload = decodeBase64Url(parts[1]);
  if (!payload) return null;

  try {
    return JSON.parse(payload);
  } catch {
    return null;
  }
};

export const getAccessToken = () => localStorage.getItem(ACCESS_TOKEN_KEY);
export const getRefreshToken = () => localStorage.getItem(REFRESH_TOKEN_KEY);

export const saveAuth = (accessToken, refreshToken) => {
  localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
  localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
  return getCurrentUser();
};

export const clearAuth = () => {
  localStorage.removeItem(ACCESS_TOKEN_KEY);
  localStorage.removeItem(REFRESH_TOKEN_KEY);
};

export const getCurrentUser = () => {
  const token = getAccessToken();
  const payload = parseJwt(token);
  if (!payload) return null;

  return {
    id: payload.id,
    email: payload.sub || payload.email,
    role: payload.role || null,
    accessToken: token,
  };
};

export const isAuthenticated = () => !!getCurrentUser();

export const isAdmin = () => {
  const user = getCurrentUser();
  return user?.role === 'ADMIN';
};

export const getUserDisplayName = () => {
  const user = getCurrentUser();
  return user?.email || '';
};
