import React from 'react';
import './App.css';
import EstudianteGestion from './components/EstudianteGestion';

function App() {
  return (
      <div className="App">
        <header className="App-header">
          <h1>Registro de Inasistencias</h1>
          <p>Aquí podrás gestionar las inasistencias de los estudiantes.</p>
        </header>
        <main className="App-main">
          <EstudianteGestion /> {/* Utiliza el componente de gestión de estudiantes */}
        </main>
        <footer className="App-footer">
          <p>&copy; 2025 Registro de Inasistencias</p>
        </footer>
      </div>
  );
}

export default App;