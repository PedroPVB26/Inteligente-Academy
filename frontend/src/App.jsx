import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

import Usuarios from "./pages/Usuarios";
import Cursos from "./pages/Cursos";
import Etiquetas from "./pages/Etiquetas";

function App() {

  return (
    <BrowserRouter>

      <div style={{ padding: "20px" }}>

        <h1>Sistema</h1>

        <nav>
          <Link to="/usuarios">Usuários</Link>
          {" | "}
          <Link to="/cursos">Cursos</Link>
          {" | "}
          <Link to="/etiquetas">Etiquetas</Link>
        </nav>

        <hr />

        <Routes>

          <Route path="/usuarios" element={<Usuarios />} />

          <Route path="/cursos" element={<Cursos />} />

          <Route path="/etiquetas" element={<Etiquetas />} />

        </Routes>

      </div>

    </BrowserRouter>
  );
}

export default App;