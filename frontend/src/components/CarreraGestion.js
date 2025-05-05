import React from 'react';

function CarreraGestion() {
    return (
        <div>
            <h2>Gestión de Carreras</h2>
            <p>Aquí podrás crear, listar, actualizar y eliminar carreras.</p>

            <h3>Crear Nueva Carrera</h3>
            <form>
                <div>
                    <label htmlFor="nombre">Nombre:</label>
                    <input type="text" id="nombre" />
                </div>
                <div>
                    <label htmlFor="descripcion">Descripción:</label>
                    <textarea id="descripcion"></textarea>
                </div>
                <button type="submit">Crear Carrera</button>
            </form>

            <h3>Lista de Carreras</h3>
            {/* Aquí mostraremos la lista de carreras */}
            <p>Cargando lista de carreras...</p>

            <h3>Acciones</h3>
            {/* Aquí irán los botones para actualizar y eliminar */}
        </div>
    );
}

export default CarreraGestion;