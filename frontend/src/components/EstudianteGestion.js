import React from 'react';

function EstudianteGestion() {
    return (
        <div>
            <h2>Gestión de Estudiantes</h2>
            <p>Aquí podrás crear, listar, actualizar y eliminar estudiantes.</p>

            <h3>Crear Nuevo Estudiante</h3>
            <form>
                <div>
                    <label htmlFor="nombre">Nombre:</label>
                    <input type="text" id="nombre" />
                </div>
                <div>
                    <label htmlFor="apellido">Apellido:</label>
                    <input type="text" id="apellido" />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input type="email" id="email" />
                </div>
                <div>
                    <label htmlFor="carrera">Carrera ID:</label>
                    <input type="number" id="carrera" />
                </div>
                <button type="submit">Crear Estudiante</button>
            </form>

            <h3>Lista de Estudiantes</h3>
            {/* Aquí mostraremos la lista de estudiantes */}
            <p>Cargando lista de estudiantes...</p>

            <h3>Acciones</h3>
            {/* Aquí irán los botones para actualizar y eliminar */}
        </div>
    );
}

export default EstudianteGestion;