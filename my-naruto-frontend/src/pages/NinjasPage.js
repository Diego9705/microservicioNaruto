import React, { useState, useEffect } from 'react';
import * as ninjasApi from '../bff/ninjas';
import * as jutsusApi from '../bff/jutsus';
import * as aldeasApi from '../bff/aldeas';
import NinjaForm from '../components/NinjaForm';

function NinjasPage() {
    const [ninjas, setNinjas] = useState([]);
    const [jutsus, setJutsus] = useState([]);
    const [aldeas, setAldeas] = useState([]);
    const [selectedNinjaIdForJutsu, setSelectedNinjaIdForJutsu] = useState('');
    const [selectedJutsuId, setSelectedJutsuId] = useState('');
    const [selectedNinjaIdForAldea, setSelectedNinjaIdForAldea] = useState('');
    const [selectedAldeaId, setSelectedAldeaId] = useState('');
    const [editNinjaData, setEditNinjaData] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            setLoading(true);
            const [ninjasRes, jutsusRes, aldeasRes] = await Promise.all([
                ninjasApi.getAllNinjas(),
                jutsusApi.getAllJutsus(),
                aldeasApi.getAllAldeas()
            ]);
            setNinjas(ninjasRes.data);
            setJutsus(jutsusRes.data);
            setAldeas(aldeasRes.data);
            setError(null);
        } catch (err) {
            setError('Error al cargar datos de ninjas, jutsus o aldeas.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleCreateNinja = async (ninjaData, aldeaIdToAssign) => {
        try {
            const response = await ninjasApi.createNinja(ninjaData);
            const newNinja = response.data;

            if (aldeaIdToAssign) {
                const parsedAldeaId = parseInt(aldeaIdToAssign);
                if (!isNaN(parsedAldeaId)) {
                    await ninjasApi.connectAldeaToNinja(newNinja.id, parsedAldeaId);
                }
            }
            fetchData();
        } catch (err) {
            setError('Error al crear el ninja o asignar la aldea.');
            console.error(err);
        }
    };

    const handleUpdateNinja = async (ninjaData) => {
        try {
            await ninjasApi.updateNinja(ninjaData);
            setEditNinjaData(null);
            fetchData();
        } catch (err) {
            setError('Error al actualizar el ninja.');
            console.error(err);
        }
    };

    const handleDeleteNinja = async (id) => {
        try {
            await ninjasApi.deleteNinja(id);
            fetchData();
        } catch (err) {
            setError('Error al eliminar el ninja.');
            console.error(err);
        }
    };

    const handleConnectJutsu = async () => {
        if (!selectedNinjaIdForJutsu || !selectedJutsuId) {
            setError('Selecciona un ninja y un jutsu.');
            return;
        }
        const ninjaId = parseInt(selectedNinjaIdForJutsu);
        const jutsuId = parseInt(selectedJutsuId);

        if (isNaN(ninjaId) || isNaN(jutsuId)) {
            setError('IDs de ninja o jutsu no válidos.');
            return;
        }

        try {
            await ninjasApi.connectJutsuToNinja(ninjaId, jutsuId);
            setSelectedNinjaIdForJutsu('');
            setSelectedJutsuId('');
            fetchData();
        } catch (err) {
            setError('Error al conectar jutsu al ninja. El ninja ya podría tener este jutsu.');
            console.error(err);
        }
    };

    const handleConnectAldea = async () => {
        if (!selectedNinjaIdForAldea || !selectedAldeaId) {
            setError('Selecciona un ninja y una aldea.');
            return;
        }
        const ninjaId = parseInt(selectedNinjaIdForAldea);
        const aldeaId = parseInt(selectedAldeaId);

        if (isNaN(ninjaId) || isNaN(aldeaId)) {
            setError('IDs de ninja o aldea no válidos.');
            return;
        }

        try {
            await ninjasApi.connectAldeaToNinja(ninjaId, aldeaId);
            setSelectedNinjaIdForAldea('');
            setSelectedAldeaId('');
            fetchData();
        } catch (err) {
            setError('Error al conectar aldea al ninja.');
            console.error(err);
        }
    };

    const handleExportNinja = async (ninjaId, option) => {
        try {
            const response = await ninjasApi.exportNinja(ninjaId, option);
            const blob = new Blob([response.data], { type: response.headers['content-type'] });
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            let extension = option === 1 ? 'json' : option === 2 ? 'txt' : 'xml';
            link.setAttribute('download', `ninja_${ninjaId}.${extension}`);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);
        } catch (err) {
            setError('Error al exportar el ninja.');
            console.error(err);
        }
    };

    const filteredNinjas = ninjas.filter(ninja =>
        ninja.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    if (loading) return <div>Cargando ninjas...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="page-container">
            <h1>Gestión de Ninjas</h1>

            <input
                type="text"
                placeholder="Buscar ninja..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{ marginBottom: '20px', padding: '10px', fontSize: '16px', width: '100%' }}
            />

            <NinjaForm onSubmit={handleCreateNinja} aldeas={aldeas} />

            {editNinjaData && (
                <NinjaForm
                    onSubmit={handleUpdateNinja}
                    initialData={editNinjaData}
                    buttonText="Actualizar Ninja"
                    aldeas={aldeas}
                />
            )}

            <h2>Conectar Jutsu a Ninja</h2>
            <div className="connection-section">
                <select value={selectedNinjaIdForJutsu} onChange={(e) => setSelectedNinjaIdForJutsu(e.target.value)}>
                    <option value="">Selecciona Ninja</option>
                    {ninjas.map((ninja) => (
                        <option key={ninja.id} value={ninja.id}>
                            {ninja.name} (ID: {ninja.id})
                        </option>
                    ))}
                </select>
                <select value={selectedJutsuId} onChange={(e) => setSelectedJutsuId(e.target.value)}>
                    <option value="">Selecciona Jutsu</option>
                    {jutsus.map((jutsu) => (
                        <option key={jutsu.id} value={jutsu.id}>
                            {jutsu.name} (ID: {jutsu.id})
                        </option>
                    ))}
                </select>
                <button onClick={handleConnectJutsu}>Aprender Jutsu</button>
            </div>

            <h2>Asignar Aldea a Ninja (Manual o Reasignación)</h2>
            <div className="connection-section">
                <select value={selectedNinjaIdForAldea} onChange={(e) => setSelectedNinjaIdForAldea(e.target.value)}>
                    <option value="">Selecciona Ninja</option>
                    {ninjas.map((ninja) => (
                        <option key={ninja.id} value={ninja.id}>
                            {ninja.name} (ID: {ninja.id}) - Aldea: {ninja.aldea}
                        </option>
                    ))}
                </select>
                <select value={selectedAldeaId} onChange={(e) => setSelectedAldeaId(e.target.value)}>
                    <option value="">Selecciona Aldea</option>
                    {aldeas.map((aldea) => (
                        <option key={aldea.id} value={aldea.id}>
                            {aldea.name} (ID: {aldea.id})
                        </option>
                    ))}
                </select>
                <button onClick={handleConnectAldea}>Asignar Aldea</button>
            </div>

            <h2>Lista de Ninjas</h2>
            {filteredNinjas.length === 0 ? (
                <p>No hay ninjas creados aún.</p>
            ) : (
                <ul className="entity-list">
                    {filteredNinjas.map((ninja) => (
                        <li key={ninja.id}>
                            ID: {ninja.id} - Nombre: {ninja.name} - Rango: {ninja.rank} - Ataque: {ninja.atk} - Defensa: {ninja.def} - Chakra: {ninja.chakra} - Aldea: {ninja.aldea} -
                            <span> Jutsus: {ninja.jutsus ? ninja.jutsus.join(', ') : ''}</span>
                            <div className="actions">
                                <button onClick={() => setEditNinjaData(ninja)}>Editar</button>
                                <button onClick={() => handleDeleteNinja(ninja.id)} className="delete-button">Eliminar</button>
                                <button onClick={() => handleExportNinja(ninja.id, 1)}>Exportar JSON</button>
                                <button onClick={() => handleExportNinja(ninja.id, 2)}>Exportar TXT</button>
                                <button onClick={() => handleExportNinja(ninja.id, 3)}>Exportar XML</button>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default NinjasPage;
