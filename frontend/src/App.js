import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import EstudiantesPage from './pages/EstudiantesPage';
import CoordinadoresPage from './pages/CoordinadoresPage';
import InicioPage from './pages/InicioPage';

// Función para verificar si el usuario está autenticado (por ahora, siempre falso)
const isAuthenticated = () => {

    return false;
};

// Componente para proteger rutas
const PrivateRoute = ({ children }) => {
    return isAuthenticated() ? children : <Navigate to="/login" />;
};

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />
                {/* Rutas protegidas */}
                <Route path="/estudiantes" element={<PrivateRoute><EstudiantesPage /></PrivateRoute>} />
                <Route path="/coordinadores" element={<PrivateRoute><CoordinadoresPage /></PrivateRoute>} />
                {/* Ruta de inicio (puede o no requerir autenticación) */}
                <Route path="/inicio" element={<InicioPage />} />
                {/* Ruta por defecto si no coincide ninguna */}
                <Route path="/" element={<Navigate to="/login" />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;