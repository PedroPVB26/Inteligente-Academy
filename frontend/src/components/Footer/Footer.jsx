export default function Footer() {
    return (
        <footer className="footer">
            <section className="footer-content">
                <div className="footer-developers">
                    <p>Desenvolvido por:</p>
                    <ul>
                        <li>Lucas Francisco</li>
                        <li>Jocimar Borges</li>
                        <li>Pedro Paulo</li>
                        <li>Leonardo Souza</li>
                    </ul>
                </div>
                <div className="footer-links">
                    <a href="/">Termos de uso</a>
                    <a href="/">Quem somos</a>
                    <a href="/">Sobre</a>
                    <a href="/">Certificação</a>
                    <a href="/">Ajuda e suporte</a>
                    <a href="/">Envie-nos seu feedback</a>
                    <a href="/">Política de coockies</a>
                    <a href="/">Política de Privacidade</a>
                </div>
            </section>
            <section className="footer-down">
                <img src="/assets/logo.png" alt="Logo IntelliGente Academy" className="footer-logo" />
                <dialog className="footer-copyright" open>
                    <p>© 2026 IntelliGente Academy. Todos os direitos reservados.</p>
                </dialog>
                <div className="footer-social">
                    <a href="https://www.facebook.com/IntelliGenteAcademy" target="_blank" rel="noopener noreferrer">
                        <img src="/assets/facebook-icon.png" alt="Facebook" />
                    </a>
                    <a href="https://www.twitter.com/IntelliGenteAcad" target="_blank" rel="noopener noreferrer">
                        <img src="/assets/twitter-icon.png" alt="Twitter" />
                    </a>
                    <a href="https://www.instagram.com/company/intelligente-academy" target="_blank" rel="noopener noreferrer">
                        <img src="/assets/instagram-icon.png" alt="Instagram" />
                    </a>
                </div>
            </section>
        </footer>
    );
}