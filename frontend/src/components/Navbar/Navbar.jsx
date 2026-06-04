import { Link } from 'react-router-dom'; // usado para as páginas não recarregarem do zero
import logoNavbar from '../../assets/logo_reduzida_navbar.svg';
import './Navbar.css';
import { useTranslation } from "react-i18next";

export default function Navbar(){
    const { t } = useTranslation();
    return (
        <nav>
            <div className='navbar-container'>
                <Link to="/"><img src={logoNavbar} alt="Logo Navbar" /></Link>
                <div className='navbar-links'>
                    <Link to="/admin/users">{t("users.title")}</Link>
                    <Link to="/admin/courses">{t("courses.title")}</Link>
                    <Link to="/admin/tags">{t("tags.title")}</Link>
                </div>
            </div>
        </nav>
    )
}