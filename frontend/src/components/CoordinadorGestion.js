import React from 'react';

function CoordinadorGestion() {
    return (
        <div>
            <h2>Gestión de Coordinadores</h2>
            <p>Aquí podrás crear, listar, actualizar y eliminar coordinadores.</p>

            <h3>Crear Nuevo Coordinador</h3>
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
                    <label htmlFor="cif">CIF:</label>
                    <input type="text" id="cif" />
                </div>
                <div>
                    <label htmlFor="telefono">Teléfono:</label>
                    <input type="tel" id="telefono" />
                </div>
                <div>
                    <label htmlFor="carrera">Carrera ID:</label>
                    <input type="number" id="carrera" />
                </div>
                <button type="submit">Crear Coordinador</button>
            </form>

            <h3>Lista de Coordinadores</h3>
            {/* Aquí mostraremos la lista de coordinadores */}
            <p>Cargando lista de coordinadores...</p>

            <h3>Acciones</h3>
            {/* Aquí irán los botones para actualizar y eliminar */}
        </div>
    );
}

export default CoordinadorGestion;