import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useEffect, useState } from "react";
import Home from './pages/Home'; 
import Certificates from './pages/Certificates';
import About from './pages/About';
import Login from './pages/Login';
import UserRegister from './pages/UserRegister';
import Courses from './pages/Courses';
import Usuarios from "./admin/Users";
import Cursos from "./admin/RegisterCourses";
import Etiquetas from "./pages/Tags";
import Navbar from "./components/Navbar/Navbar";
import Footer from "./components/Footer/Footer";


function App() {
  const [authModal, setAuthModal] = useState(null);

  useEffect(() => {
    document.body.style.overflow = authModal ? "hidden" : "";

    return () => {
      document.body.style.overflow = "";
    };
  }, [authModal]);

  const closeAuthModal = () => setAuthModal(null);

  return (
    <BrowserRouter>
      <div className={authModal ? "app-shell app-shell--blurred" : "app-shell"}>
        <Navbar onOpenLogin={() => setAuthModal("login")} />

        <div className="app-content">

          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/courses" element={<Courses />} />
            <Route path="/certificates" element={<Certificates />} />
            <Route path="/about" element={<About />} />

            {/* ADMIN */}
            <Route path="/admin/users" element={<Usuarios />} />
            <Route path="/admin/courses" element={<Cursos />} />
            <Route path="/admin/tags" element={<Etiquetas />} />

          </Routes>

        </div>
        <Footer />
      </div>

      {authModal === "login" && (
        <Login
          onClose={closeAuthModal}
          onOpenRegister={() => setAuthModal("register")}
        />
      )}

      {authModal === "register" && (
        <UserRegister onClose={closeAuthModal} />
      )}
    </BrowserRouter>
  );
}

export default App;