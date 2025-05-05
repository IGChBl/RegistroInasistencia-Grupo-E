import React, { useState } from 'react';

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError('');

        try {
            const response = await fetch('/login', { // <---- URL corregida a '/login'
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded', // Spring Security por defecto espera este tipo
                },
                body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`,
            });

            if (response.ok) {
                // Login exitoso, aquí deberías manejar la sesión/token y redirigir al usuario
                console.log('Login successful!');
                // Por ahora, solo mostraremos un mensaje. Luego implementaremos la redirección basada en el rol.
            } else {
                const errorData = await response.text(); // O response.json() si tu backend envía JSON en caso de error
                setError(`Login failed: ${errorData}`);
            }
        } catch (error) {
            setError('There was an error connecting to the server.');
            console.error('Login error:', error);
        }
    };

    return (
        <div>
            <h2>Login</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={handleUsernameChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={handlePasswordChange}
                        required
                    />
                </div>
                <button type="submit">Log In</button>
            </form>
        </div>
    );
}

export default Login;