import './Footer.css';
import logoFooter from '../../assets/logo_horizontal.svg';
import twitterIcon from '../../assets/Twitter_logo.png';
import instagramIcon from '../../assets/Instagram_icon.png';
import facebookIcon from '../../assets/Facebook_logo.png';


export default function Footer() {
    return (
        <footer className="footer">
            <section className="footer-content">
                <div className="footer-developers">
                    <p>Desenvolvido por:</p>
                    <ul>
                        <li>Jocimar Borges Júnior</li>
                        <li>Lucas Francisco Alves Costa</li>
                        <li>Pedro Paulo Valent Bittencourt</li>
                        <li>Leonardo Silva e Cruz</li>
                    </ul>
                </div>
                <div className="footer-links-col">
                    <a href="/">Termos de Uso</a>
                    <a href="/">Quem Somos</a>
                    <a href="/">Sobre</a>
                    <a href="/">Certificação</a>
                </div>

                <div className="footer-links-col">
                    <a href="/">Ajuda e Suporte</a>
                    <a href="/">Envie-nos seu feedback</a>
                    <a href="/">Política de Cookies</a>
                    <a href="/">Política de Privacidade</a>
                </div>
            </section>
            <section className="footer-down">
                {/* Verifique se a logo está exatamente nesta pasta dentro de 'public' */}
                <img src={logoFooter} alt="Logo IntelliGente Academy" className="footer-logo" />

                <p className="footer-copyright">
                    Copyright © IntelliGente 2026. Todos os direitos reservados.
                </p>

                <div className="footer-social">
                    <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">
                        <img src={twitterIcon} alt="Twitter" />
                    </a>
                    <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                        <img src={instagramIcon} alt="Instagram" />
                    </a>
                    <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">
                        <img src={facebookIcon} alt="Facebook" />
                    </a>
                </div>
            </section>
        </footer>
    );
}