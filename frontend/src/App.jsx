import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import Home from './pages/Home'; 
import Login from './pages/Login';
import Usuarios from "./admin/Users";
import Cursos from "./pages/RegisterCourses";
import Etiquetas from "./pages/Tags";
import Navbar from "./components/Navbar/Navbar";
import Footer from "./components/Footer/Footer";

function App() {
  const { t } = useTranslation();
  return (
    <BrowserRouter>
      <Navbar />

      <div style={{ padding: "20px" }}>

        <h1>{t("system.title")}</h1>

        <hr />

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />

          // ADMIN
          <Route path="/admin/users" element={<Usuarios />} />
          <Route path="/admin/courses" element={<Cursos />} />
          <Route path="/admin/tags" element={<Etiquetas />} />

        </Routes>

      </div>
      <Footer />
    </BrowserRouter>
  );
}

export default App;