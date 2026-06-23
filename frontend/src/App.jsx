import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import Home from './pages/Home';
import Certificates from './pages/Certificates';
import About from './pages/About';
import Login from './pages/Login';
import UserRegister from './pages/UserRegister';
import Courses from './pages/Courses';
import DetailsCourses from './pages/DetailsCourses';
import Usuarios from "./admin/Users";
import Cursos from "./admin/RegisterCourses";
import Etiquetas from "./pages/Tags";
import Profile from './pages/Profile';
import Navbar from "./components/Navbar/Navbar";
import ProtectedRoute from './components/Navbar/ProtectedRoute';
import { clearAuth, getCurrentUser } from './services/authService';
import Footer from "./components/Footer/Footer";


function App() {
  const [authModal, setAuthModal] = useState(null);
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    setCurrentUser(getCurrentUser());
  }, []);

  useEffect(() => {
    document.body.style.overflow = authModal ? "hidden" : "";

    return () => {
      document.body.style.overflow = "";
    };
  }, [authModal]);

  const closeAuthModal = () => setAuthModal(null);

  const handleLoginSuccess = () => {
    setCurrentUser(getCurrentUser());
    closeAuthModal();
  };

  const handleLogout = () => {
    clearAuth();
    setCurrentUser(null);
  };

  return (
    <BrowserRouter>
      <div className={authModal ? "app-shell app-shell--blurred" : "app-shell"}>
        <Navbar
          onOpenLogin={() => setAuthModal("login")}
          currentUser={currentUser}
          onLogout={handleLogout}
        />

        <div className="app-content">

          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/courses" element={<Courses />} />
            <Route path="/courses/:courseId" element={<DetailsCourses />} />
            <Route path="/certificates" element={<Certificates />} />
            <Route path="/about" element={<About />} />
            <Route path="/profile" element={currentUser ? <Profile /> : <Navigate to="/" replace />} />

            {/* ADMIN */}
            <Route path="/admin/users" element={<ProtectedRoute><Usuarios /></ProtectedRoute>} />
            <Route path="/admin/courses" element={<ProtectedRoute><Cursos /></ProtectedRoute>} />
            <Route path="/admin/tags" element={<ProtectedRoute><Etiquetas /></ProtectedRoute>} />

          </Routes>

        </div>
        <Footer />
      </div>

      {authModal === "login" && (
        <Login
          onClose={closeAuthModal}
          onOpenRegister={() => setAuthModal("register")}
          onLoginSuccess={handleLoginSuccess}
        />
      )}

      {authModal === "register" && (
        <UserRegister onClose={closeAuthModal} />
      )}
    </BrowserRouter>
  );
}

export default App;