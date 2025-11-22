import React, { useState, useEffect } from 'react';
import { getNinjaById }from '../bff/ninjas';

function MisionForm({ onSubmit, initialData = {}, buttonText = 'Crear Misión', ninjas = [] }) {

    var resultado = null;
    const initialNinja = ninjas.find(n => n.name === initialData.ninja) || null;
    const fetchNinjaData = async (ninjaName) => {
        const response = await getNinjaById(initialNinja.id);
        resultado = response.data;
    }
    if (initialNinja != null) {
        fetchNinjaData(initialNinja);
    }


    const [name, setName] = useState(initialData.name || '');
    const [rank, setRank] = useState(initialData.rank || 'D');
    const [recompensa, setRecompensa] = useState(initialData.recompensa || 0);
    const [requisitorango, setRequisitoRango] = useState(initialData.requisitorango || 'Genin');
    const [ninjaName, setNinjaName] = useState(initialData.ninja|| '');
    const [prevId, setPrevId] = useState(initialData.id);
    const [ninja, setNinja]= useState(resultado);


    useEffect(() => {

        const fetchNinjaData = async (ninjaName) => {

            const foundNinja = ninjas.find(n => n.name === ninjaName)|| null;

            if (foundNinja == null){
                setNinja(null)

            } else {
                const response = await getNinjaById(foundNinja.id);
                setNinja(response.data);
            }


        }

        if (initialData && initialData.id && initialData.name !== "") {


            fetchNinjaData(ninjaName);
            setName(initialData.name || '');
            setRank(initialData.rank || 'D');
            setRecompensa(initialData.recompensa || 0);
            setRequisitoRango(initialData.requisitorango || 'Genin');
            setPrevId(initialData.id);
        }
    }, [initialData, prevId, ninjas, ninjaName,initialNinja ]);

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(
            {
                id: initialData.id,
                name,
                rank,
                recompensa: parseInt(recompensa),
                requisitorango,
                ninja : ninja
            }
        );
        if (!initialData.id) {
            setName('');
            setRank('D');
            setRecompensa(0);
            setRequisitoRango('Genin');
            setNinja(null);
            setPrevId(null);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="form-container">
            <h3>{buttonText}</h3>
            <input
                type="text"
                placeholder="Nombre de la Misión"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />
            <label>
                Rango (Misión):
                <select value={rank} onChange={(e) => setRank(e.target.value)}>
                    <option value="D">D</option>
                    <option value="C">C</option>
                    <option value="B">B</option>
                    <option value="A">A</option>
                    <option value="S">S</option>
                </select>
            </label>
            <input
                type="number"
                placeholder="Recompensa"
                value={recompensa}
                onChange={(e) => setRecompensa(e.target.value)}
                required
            />
            <label>
                Requisito de Rango (Ninja):
                <select value={requisitorango} onChange={(e) => setRequisitoRango(e.target.value)}>
                    <option value="Genin">Genin</option>
                    <option value="Chunin">Chunin</option>
                    <option value="Jonin">Jonin</option>
                </select>
            </label>


            <label>
                Asignar Ninja:
                <select
                    value={ninjaName}
                    onChange={(e) => {
                        const newNinjaName = e.target.value;
                        setNinjaName(newNinjaName);
                        const selectedNinja = getNinjaById(ninjas.find(ninja => ninja.name === newNinjaName).id);
                        selectedNinja.then(response => {
                            setNinja(response.data);

                        })


                        }
                    }>

                    <option value="">(Ninguno)</option>
                    {ninjas.map((ninja) => (
                        <option key={ninja.id} value={ninja.name}>
                            {ninja.name} (Rango: {ninja.rank})
                        </option>
                    ))}
                </select>
            </label>

            <button type="submit">{buttonText}</button>
        </form>
    );
}

export default MisionForm;
