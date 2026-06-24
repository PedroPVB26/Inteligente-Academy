import React from 'react';
import './navbar.css';
import { useNavigate } from "react-router-dom";
import logoHorizontal from '../../assets/logo_reduzida_navbar.svg';
import UserMenu from './UserMenu';
import { useTranslation } from 'react-i18next';

function Navbar({ onOpenLogin, currentUser, onLogout }) {
    const navigate = useNavigate();
    const { i18n } = useTranslation();

    const currentLang = i18n.language && i18n.language.startsWith('pt') ? 'pt' : 'en';
    const toggleLang = () => {
        const next = currentLang === 'pt' ? 'en' : 'pt';
        i18n.changeLanguage(next);
    };

    return (
        <nav className="navbar">
            {/* Lado Esquerdo: Logo e Links Principais */}
            <div className="nav-left">
                <div className="nav-logo-container">
                    <img src={logoHorizontal} alt="Logo Academy" className="nav-logo" onClick={() => navigate("/")} />
                </div>

                <div className="nav-links">
                    <a href="#sobre" className="nav-link" onClick={() => navigate("/about")}>Sobre</a>
                    <a href="#certificados" className="nav-link" onClick={() => navigate("/certificates")}>Certificados</a>
                    <a href="#cursos" className="nav-link" onClick={() => navigate("/courses")}>Cursos</a>
                </div>
            </div>

            {/* Lado Direito: Seleção de Idioma, Dark Mode e Entrar */}
            <div className="nav-right">
                {/*{/* Seletor de Idioma }
                <div className="nav-language">
                    <span className="globe-icon">🌐</span>
                    <span className="arrow-down">⌃</span>
                </div>

                {/* Botão Dark Mode (Lua) 
                <button className="nav-theme-toggle">
                    <span className="moon-icon">🌙</span>
                </button>*/}

                <button
                    type="button"
                    className="nav-lang-toggle"
                    onClick={toggleLang}
                    aria-label="Trocar idioma"
                >
                    {currentLang === 'pt' ? 'PT' : 'EN'}
                </button>

                {currentUser ? (
                    <UserMenu onLogout={onLogout} />
                ) : (
                    <button className="btn-login" onClick={onOpenLogin}>Entrar</button>
                )}
            </div>
        </nav>
    );
}

export default Navbar;