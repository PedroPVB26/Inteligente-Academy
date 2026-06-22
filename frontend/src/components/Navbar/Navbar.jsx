import React from 'react';
import './navbar.css';
import logoHorizontal from '../../assets/logo_horizontal.svg';

function Navbar() {
    return (
        <nav className="navbar">
            {/* Lado Esquerdo: Logo e Links Principais */}
            <div className="nav-left">
                <div className="nav-logo-container">
                    <img src={logoHorizontal} alt="Logo Academy" className="nav-logo" />
                </div>

                <div className="nav-links">
                    <a href="#sobre" className="nav-link">Sobre</a>
                    <a href="#certificados" className="nav-link">Certificados</a>
                    <a href="#cursos" className="nav-link">Cursos</a>
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

                {/* Botão Entrar Laranja */}
                <button className="btn-login">Entrar</button>
            </div>
        </nav>
    );
}

export default Navbar;