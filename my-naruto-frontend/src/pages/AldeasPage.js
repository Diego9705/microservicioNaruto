import React, { useState, useEffect } from 'react';
import * as aldeasApi from '../bff/aldeas';
import AldeaForm from '../components/AldeaForm';

function AldeasPage() {
    const [aldeas, setAldeas] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchAldeas();
    }, []);

    const fetchAldeas = async () => {
        try {
            setLoading(true);
            const response = await aldeasApi.getAllAldeas();
            setAldeas(response.data);
            setError(null);
        } catch (err) {
            setError('Error al cargar las aldeas.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleCreateAldea = async (aldeaData) => {
        try {
            await aldeasApi.createAldea(aldeaData);
            fetchAldeas();
        } catch (err) {
            setError('Error al crear la aldea.');
            console.error(err);
        }
    };

    const handleLoadDefaultAldeas = async () => {
        try {
            await aldeasApi.loadDefaultAldeas();
            fetchAldeas();
        } catch (err) {
            setError('Error al cargar aldeas predeterminadas.');
            console.error(err);
        }
    };

    const filteredAldeas = aldeas.filter(aldea =>
        aldea.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    if (loading) return <div>Cargando aldeas...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="page-container">
            <h1>Gestión de Aldeas</h1>

            <input
                type="text"
                placeholder="Buscar aldea..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{ marginBottom: '20px', padding: '10px', fontSize: '16px', width: '100%' }}
            />

            <AldeaForm onSubmit={handleCreateAldea} />

            <button onClick={handleLoadDefaultAldeas} className="load-defaults-button">
                Cargar Aldeas Predeterminadas
            </button>

            <h2>Lista de Aldeas</h2>
            {filteredAldeas.length === 0 ? (
                <p>No hay aldeas creadas aún.</p>
            ) : (
                <ul className="entity-list">
                    {filteredAldeas.map((aldea) => (
                        <li key={aldea.id}>
                            ID: {aldea.id} - Nombre: {aldea.name}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default AldeasPage;