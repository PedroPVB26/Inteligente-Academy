import { Link } from 'react-router-dom'; // usado para as páginas não recarregarem do zero
import logoNavbar from '../../assets/logo_reduzida_navbar.svg';

export default function Navbar(){
    return (
        <nav>
            <div className='navbar-container'>
                <Link to="/"><img src={logoNavbar} alt="Logo Navbar" /></Link>
                <div className='navbar-links'>
                    <Link to="/admin">Usuários</Link>
                    <Link to="/admin/cursos">Cursos</Link>
                    <Link to="/admin/etiquetas">Etiquetas</Link>
                </div>
            </div>
        </nav>
    )
}