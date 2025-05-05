import React from 'react';
import './App.css';
// import EstudianteGestion from './components/EstudianteGestion';
// import CoordinadorGestion from './components/CoordinadorGestion';
// import CarreraGestion from './components/CarreraGestion';
// import JustificacionGestion from "./components/JustificacionGestion";
import Login from './components/Login'; // Importa el componente Login

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <h1>Registro de Inasistencias</h1>
                <p>Aquí podrás gestionar las inasistencias de los estudiantes y coordinadores.</p>
            </header>
            <main className="App-main">
                <Login /> {/* Renderiza el formulario de login */}
                {/* <EstudianteGestion /> */}
                {/* <CoordinadorGestion /> */}
                {/* <CarreraGestion /> */}
                {/* <JustificacionGestion /> */}
            </main>
            <footer className="App-footer">
                <p>&copy; 2025 Registro de Inasistencias</p>
            </footer>
        </div>
    );
}

export default App;