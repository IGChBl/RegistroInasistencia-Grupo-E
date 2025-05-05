import React from 'react';
import './App.css';
import EstudianteGestion from './components/EstudianteGestion';
import CoordinadorGestion from './components/CoordinadorGestion';
import CarreraGestion from './components/CarreraGestion';
import JustificacionGestion from "./components/JustificacionGestion";

function App() {
  return (
      <div className="App">
        <header className="App-header">
          <h1>Registro de Inasistencias</h1>
          <p>Aquí podrás gestionar las inasistencias de los estudiantes, coordinadores y carreras.</p>
        </header>
        <main className="App-main">
          <EstudianteGestion />
          <CoordinadorGestion />
          <CarreraGestion /> {/* Utiliza el componente de gestión de carreras */}
          <JustificacionGestion /> {/* Utiliza el componente de gestión de justificación */}
        </main>

        <footer className="App-footer">
          <p>&copy; 2025 Registro de Inasistencias</p>
        </footer>
      </div>
  );
}

export default App;