import React from 'react';

function JustificacionGestion() {
    return (
        <div>
            <h2>Gestión de Justificaciones</h2>
            <p>Aquí podrás crear, listar, actualizar y eliminar justificaciones.</p>

            <h3>Crear Nueva Justificación</h3>
            <form>
                <div>
                    <label htmlFor="estudiante">Estudiante:</label>
                    <select id="estudiante">
                        <option value="">Seleccionar estudiante</option>
                        {/* Las opciones de estudiantes se cargarán aquí */}
                    </select>
                </div>
                <div>
                    <label htmlFor="carrera">Carrera:</label>
                    <select id="carrera">
                        <option value="">Seleccionar carrera</option>
                        {/* Las opciones de carreras se cargarán aquí */}
                    </select>
                </div>
                <div>
                    <label htmlFor="fecha_ini">Fecha de Inicio:</label>
                    <input type="date" id="fecha_ini" />
                </div>
                <div>
                    <label htmlFor="fecha_fin">Fecha de Fin:</label>
                    <input type="date" id="fecha_fin" />
                </div>
                <div>
                    <label htmlFor="motivo">Motivo:</label>
                    <textarea id="motivo"></textarea>
                </div>
                <div>
                    <label htmlFor="evidencia">Evidencia:</label>
                    <input type="text" id="evidencia" />
                </div>
                <button type="submit">Crear Justificación</button>
            </form>

            <h3>Lista de Justificaciones</h3>
            {/* Aquí mostraremos la lista de justificaciones */}
            <p>Cargando lista de justificaciones...</p>

            <h3>Acciones</h3>
            {/* Aquí irán los botones para actualizar y eliminar */}
        </div>
    );
}

export default JustificacionGestion;