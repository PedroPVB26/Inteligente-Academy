import { useEffect, useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { clearAuth, getCurrentUser, getAccessToken } from '../services/authService';

const API = import.meta.env.VITE_API_URL ?? '';

export default function Profile() {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState('perfil');
  const [loading, setLoading] = useState(true);
  const [userDetails, setUserDetails] = useState(null);
  const [error, setError] = useState('');

  const current = getCurrentUser();
  const [redirect, setRedirect] = useState(false);

  useEffect(() => {
    if (!current) return;

    const abort = new AbortController();
    const fetchUser = async () => {
      setLoading(true);
      try {
        const res = await fetch(`${API}/users/${current.id}`, {
          headers: {
            Authorization: `Bearer ${getAccessToken()}`,
            'Content-Type': 'application/json',
          },
          signal: abort.signal,
        });

        if (!res.ok) {
          if (res.status === 401 || res.status === 410) {
            clearAuth();
            setRedirect(true);
            return;
          }
          const errPayload = await res.json().catch(() => ({}));
          throw new Error(errPayload.message || `HTTP ${res.status}`);
        }

        const data = await res.json();
        setUserDetails(data);
      } catch (err) {
        if (err.name !== 'AbortError') setError(err.message || 'Erro ao buscar usuário');
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
    return () => abort.abort();
  }, [current]);

  if (!current || redirect) return <Navigate to="/" replace />;

  const [pwdForm, setPwdForm] = useState({ currentPassword: '', newPassword: '', confirmPassword: '' });

  const handlePwdChange = (e) => setPwdForm({ ...pwdForm, [e.target.name]: e.target.value });

  const handleChangePassword = async (e) => {
    e.preventDefault();
    setError('');

    if (pwdForm.newPassword !== pwdForm.confirmPassword) {
      setError('A nova senha e a confirmação não coincidem.');
      return;
    }

    try {
      const res = await fetch(`${API}/users/password`, {
        method: 'PUT',
        headers: {
          Authorization: `Bearer ${getAccessToken()}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(pwdForm),
      });

      if (!res.ok) {
        const err = await res.json().catch(() => ({}));
        throw new Error(err.message || `HTTP ${res.status}`);
      }

      alert('Senha alterada com sucesso.');
      setPwdForm({ currentPassword: '', newPassword: '', confirmPassword: '' });
    } catch (err) {
      setError(err.message || 'Erro ao alterar senha');
    }
  };

  return (
    <div className="profile-page">
      <aside className="profile-aside">
        <div className="profile-avatar">{current.email?.charAt(0).toUpperCase()}</div>
        <h2>{current.email?.split('@')[0]}</h2>

        <nav className="profile-nav">
          <button className={activeTab === 'perfil' ? 'active' : ''} onClick={() => setActiveTab('perfil')}>
            Perfil
          </button>
          <button className={activeTab === 'seguranca' ? 'active' : ''} onClick={() => setActiveTab('seguranca')}>
            Segurança da conta
          </button>
        </nav>
      </aside>

      <main className="profile-main">
        <h1>Perfil público</h1>
        {loading && <p>Carregando...</p>}
        {error && <p className="error">{error}</p>}

        {!loading && error && error.includes('410') && (
          <div className="session-expired">
            <p>Sessão expirada. Você será redirecionado para login.</p>
          </div>
        )}

        {!loading && userDetails && !error?.includes('410') && activeTab === 'perfil' && (
          <section>
            <h3>Dados básicos</h3>
            <div>
              <label>Nome completo</label>
              <input type="text" value={userDetails.name || ''} readOnly />
            </div>
            <div>
              <label>E-mail</label>
              <input type="email" value={userDetails.email || ''} readOnly />
            </div>
            <div>
              <label>CPF</label>
              <input type="text" value={userDetails.cpf || ''} readOnly />
            </div>
            <div>
              <label>Função</label>
              <input type="text" value={userDetails.userRole || ''} readOnly />
            </div>
            <p className="muted">Edição de perfil não disponível via API neste endpoint.</p>
          </section>
        )}

        {!loading && activeTab === 'seguranca' && (
          <section>
            <h3>Segurança da conta</h3>
            <form onSubmit={handleChangePassword} className="change-password-form">
              <div>
                <label>Senha atual</label>
                <input name="currentPassword" type="password" value={pwdForm.currentPassword} onChange={handlePwdChange} required />
              </div>
              <div>
                <label>Nova senha</label>
                <input name="newPassword" type="password" value={pwdForm.newPassword} onChange={handlePwdChange} required minLength={8} />
              </div>
              <div>
                <label>Confirmar nova senha</label>
                <input name="confirmPassword" type="password" value={pwdForm.confirmPassword} onChange={handlePwdChange} required />
              </div>
              <button type="submit">Alterar senha</button>
            </form>
          </section>
        )}
      </main>
    </div>
  );
}