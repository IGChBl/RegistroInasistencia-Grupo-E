import React, { useState, useEffect } from 'react';

function EstudianteGestion() {
    const [estudiantes, setEstudiantes] = useState([]);
    const [nombre, setNombre] = useState('');
    const [apellido, setApellido] = useState('');
    const [email, setEmail] = useState('');
    const [carrera, setCarrera] = useState('');

    useEffect(() => {
        fetch('http://localhost:8080/api/estudiante')
            .then(response => response.json())
            .then(data => {
                setEstudiantes(data.data);
            })
            .catch(error => {
                console.error('Error al obtener estudiantes:', error);
            });
    }, []);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        switch (name) {
            case 'nombre':
                setNombre(value);
                break;
            case 'apellido':
                setApellido(value);
                break;
            case 'email':
                setEmail(value);
                break;
            case 'carrera':
                setCarrera(value);
                break;
            default:
                break;
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const nuevoEstudiante = {
            nombre: nombre,
            apellido: apellido,
            email: email,
            carrera: { id: parseInt(carrera) || null }, // Asumiendo que envías el ID de la carrera
        };

        fetch('http://localhost:8080/api/estudiante', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(nuevoEstudiante),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Estudiante creado:', data);
                // Después de crear, podrías recargar la lista de estudiantes
                fetch('http://localhost:8080/api/estudiante')
                    .then(res => res.json())
                    .then(newData => setEstudiantes(newData.data))
                    .catch(err => console.error('Error al recargar estudiantes:', err));
                // Limpiar el formulario
                setNombre('');
                setApellido('');
                setEmail('');
                setCarrera('');
            })
            .catch(error => {
                console.error('Error al crear estudiante:', error);
            });
    };

    return (
        <div>
            <h2>Gestión de Estudiantes</h2>
            <p>Aquí podrás crear, listar, actualizar y eliminar estudiantes.</p>

            <h3>Crear Nuevo Estudiante</h3>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="nombre">Nombre:</label>
                    <input type="text" id="nombre" name="nombre" value={nombre} onChange={handleInputChange} />
                </div>
                <div>
                    <label htmlFor="apellido">Apellido:</label>
                    <input type="text" id="apellido" name="apellido" value={apellido} onChange={handleInputChange} />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input type="email" id="email" name="email" value={email} onChange={handleInputChange} />
                </div>
                <div>
                    <label htmlFor="carrera">Carrera ID:</label>
                    <input type="number" id="carrera" name="carrera" value={carrera} onChange={handleInputChange} />
                </div>
                <button type="submit">Crear Estudiante</button>
            </form>

            <h3>Lista de Estudiantes</h3>
            {estudiantes.length > 0 ? (
                <ul>
                    {estudiantes.map(estudiante => (
                        <li key={estudiante.id}>
                            {estudiante.nombre} {estudiante.apellido} ({estudiante.email}) - Carrera ID: {estudiante.carrera ? estudiante.carrera.id : 'N/A'}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No hay estudiantes registrados.</p>
            )}

            <h3>Acciones</h3>
            {/* Aquí irán los botones para actualizar y eliminar (lo implementaremos más adelante) */}
        </div>
    );
}

export default EstudianteGestion;