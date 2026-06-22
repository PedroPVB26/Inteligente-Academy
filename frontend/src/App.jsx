import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import Home from './pages/Home'; 
import Certificates from './pages/Certificates';
import About from './pages/About';
import Login from './pages/Login';
import Courses from './pages/Courses';
import Usuarios from "./admin/Users";
import Cursos from "./admin/RegisterCourses";
import Etiquetas from "./pages/Tags";
import Navbar from "./components/Navbar/Navbar";
import Footer from "./components/Footer/Footer";


function App() {
  const { t } = useTranslation();
  return (
    <BrowserRouter>
      <Navbar />

      <div style={{ padding: "20px" }}>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/courses" element={<Courses />} />
          <Route path="/certificates" element={<Certificates />} />
          <Route path="/about" element={<About />} />

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