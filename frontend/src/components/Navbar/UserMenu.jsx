import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { clearAuth, getCurrentUser } from '../../services/authService';
import './UserMenu.css';
import "../../pages/Profile.jsx"; // Importando o CSS do Profile.jsx para estilizar o menu do usuário

export default function UserMenu({ onLogout }) {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();
  const user = getCurrentUser();

  if (!user) return null;

  const userName = user.email?.split('@')[0] || 'Usuário';
  const userInitial = userName.charAt(0).toUpperCase() || 'U';

  return (
    <div className="nav-user-menu">
      <button
        type="button"
        className="btn-user-menu"
        onClick={() => setOpen((prev) => !prev)}
        aria-label="Abrir menu do usuário"
      >
        <span className="user-avatar-image" aria-hidden="true">
          {userInitial}
        </span>
      </button>

      {open && (
        <div className="user-menu-dropdown">
          <div className="user-menu-header">
            <span className="user-menu-avatar">{userInitial}</span>
            <div>
              <strong>{userName}</strong>
              <p>{user.role}</p>
            </div>
          </div>
          <button
            type="button"
            className="user-menu-item"
            onClick={() => {
              setOpen(false);
              navigate('/profile');
            }}
          >
            Editar Perfil
          </button>
          <button
            type="button"
            className="user-menu-item user-menu-logout"
            onClick={() => {
              clearAuth();
              onLogout();
              navigate('/');
            }}
          >
            Sair
          </button>
        </div>
      )}
    </div>
  );
}
