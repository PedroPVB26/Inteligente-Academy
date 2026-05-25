import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import Navbar from './components/Navbar/Navbar';
import Home from './pages/Home'; 
import Login from './pages/Login';
import Usuarios from "./admin/Usuarios";
import Cursos from "./pages/Cursos";
import Etiquetas from "./pages/Etiquetas";


function App() {
  const { t } = useTranslation();
  return (
    <BrowserRouter>
      <Navbar />

      <div style={{ padding: "20px" }}>

        <h1>{t("system.title")}</h1>

        <nav>
          <Link to="/admin/usuarios">{t("users.title")}</Link>
          {" | "}
          <Link to="/admin/cursos">{t("courses.title")}</Link>
          {" | "}
          <Link to="/admin/etiquetas">{t("tags.title")}</Link>
        </nav>

        <hr />

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/admin/usuarios" element={<Usuarios />} />

          <Route path="/admin/cursos" element={<Cursos />} />

          <Route path="/admin/etiquetas" element={<Etiquetas />} />

        </Routes>

      </div>

    </BrowserRouter>
  );
}

export default App;