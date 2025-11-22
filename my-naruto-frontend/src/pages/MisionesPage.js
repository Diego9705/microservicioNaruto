import React, { useState, useEffect } from 'react';
import * as misionesApi from '../bff/misiones';
import * as ninjasApi from '../bff/ninjas';
import MisionForm from '../components/MisionForm';

function MisionesPage() {
    const [misiones, setMisiones] = useState([]);
    const [ninjas, setNinjas] = useState([]);
    const [selectedMisionId, setSelectedMisionId] = useState('');
    const [selectedNinjaId, setSelectedNinjaId] = useState('');
    const [editMisionData, setEditMisionData] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            setLoading(true);
            const [misionesRes, ninjasRes] = await Promise.all([
                misionesApi.getAllMisiones(),
                ninjasApi.getAllNinjas()
            ]);
            setMisiones(misionesRes.data);
            setNinjas(ninjasRes.data);
            setError(null);
        } catch (err) {
            setError('Error al cargar datos de misiones o ninjas.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleCreateMision = async (misionData, ninjaIdToAssign) => {
        try {
            const response = await misionesApi.createMision(misionData);
            const newMision = response.data;

            if (ninjaIdToAssign) {
                const parsedNinjaId = parseInt(ninjaIdToAssign);
                if (!isNaN(parsedNinjaId)) {
                    await misionesApi.connectNinjaToMision(newMision.id, parsedNinjaId);
                }
            }
            fetchData();
        } catch (err) {
            setError('Error al crear la misión o asignar el ninja. Verifica rangos o si la misión ya tiene un ninja.');
            console.error(err);
        }
    };

    const handleUpdateMision = async (misionData) => {
        try {
            await misionesApi.updateMision(misionData);
            setEditMisionData(null);
            fetchData();
        } catch (err) {
            setError('Error al actualizar la misión.');
            console.error(err);
        }
    };

    const handleDeleteMision = async (id) => {
        try {
            await misionesApi.deleteMision(id);
            fetchData();
        } catch (err) {
            setError('Error al eliminar la misión.');
            console.error(err);
        }
    };

    const handleConnectNinja = async () => {
        if (!selectedMisionId || !selectedNinjaId) {
            setError('Selecciona una misión y un ninja.');
            return;
        }
        const misionId = parseInt(selectedMisionId);
        const ninjaId = parseInt(selectedNinjaId);

        /*
        if (isNaN(misionId) || isNaN(ninjaId)) {
            setError('IDs de misión o ninja no válidos.');
            return;
        }
        */

        try {
            await misionesApi.connectNinjaToMision(misionId, ninjaId);
            setSelectedMisionId('');
            setSelectedNinjaId('');
            fetchData();
        } catch (err) {
            setError('Error al conectar ninja a la misión. Verifica rangos o si la misión ya tiene un ninja.');
            console.error(err);
        }
    };

    const handleLoadDefaultMisiones = async () => {
        try {
            await misionesApi.loadDefaultMisiones();
            fetchData();
        } catch (err) {
            setError('Error al cargar misiones predeterminadas.');
            console.error(err);
        }
    };

    const handleExportMision = async (misionId, option) => {
        try {
            const response = await misionesApi.exportMision(misionId, option);
            const blob = new Blob([response.data], { type: response.headers['content-type'] });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            let extension = option === 1 ? 'json' : option === 2 ? 'txt' : 'xml';
            link.setAttribute('download', `mision_${misionId}.${extension}`);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);
        } catch (err) {
            setError('Error al exportar la misión.');
            console.error(err);
        }
    };

    const filteredMisiones = misiones.filter(mision =>
        mision.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    if (loading) return <div>Cargando misiones...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="page-container">
            <h1>Gestión de Misiones</h1>

            <input
                type="text"
                placeholder="Buscar misión..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{ marginBottom: '20px', padding: '10px', fontSize: '16px', width: '100%' }}
            />

            <MisionForm onSubmit={handleCreateMision} ninjas={ninjas} />

            {editMisionData && (
                <MisionForm
                    onSubmit={handleUpdateMision}
                    initialData={editMisionData}
                    buttonText="Actualizar Misión"
                    ninjas={ninjas}
                />
            )}

            <button onClick={handleLoadDefaultMisiones} className="load-defaults-button">
                Cargar Misiones Predeterminadas
            </button>

            <h2>Conectar Ninja a Misión (Manual)</h2>
            <div className="connection-section">
                <select value={selectedMisionId} onChange={(e) => setSelectedMisionId(e.target.value)}>
                    <option value="">Selecciona Misión</option>
                    {filteredMisiones.map((mision) => (
                        <option key={mision.id} value={mision.id}>
                            {mision.name} (ID: {mision.id}) - Ninja: {mision.ninja || 'Ninguno'}
                        </option>
                    ))}
                </select>
                <select value={selectedNinjaId} onChange={(e) => setSelectedNinjaId(e.target.value)}>
                    <option value="">Selecciona Ninja</option>
                    {ninjas.map((ninja) => (
                        <option key={ninja.id} value={ninja.id}>
                            {ninja.name} (Rango: {ninja.rank})
                        </option>
                    ))}
                </select>
                <button onClick={handleConnectNinja}>Asignar Ninja a Misión</button>
            </div>

            <h2>Lista de Misiones</h2>
            {filteredMisiones.length === 0 ? (
                <p>No hay misiones creadas aún.</p>
            ) : (
                <ul className="entity-list">
                    {filteredMisiones.map((mision) => (
                        <li key={mision.id}>
                            ID: {mision.id} - Nombre: {mision.name} - Rango: {mision.rank} - Recompensa: {mision.recompensa} - Requisito Rango: {mision.requisitorango} - Ninja: {mision.ninja}
                            <div className="actions">
                                <button onClick={() => setEditMisionData(mision)}>Editar</button>
                                <button onClick={() => handleDeleteMision(mision.id)} className="delete-button">Eliminar</button>
                                <button onClick={() => handleExportMision(mision.id, 1)}>Exportar JSON</button>
                                <button onClick={() => handleExportMision(mision.id, 2)}>Exportar TXT</button>
                                <button onClick={() => handleExportMision(mision.id, 3)}>Exportar XML</button>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default MisionesPage;