import axios from 'axios';
import * as ninjasApi from '../bff/ninjas';

const API_URL = 'http://localhost:8050/api/misiones';

export const getAllMisiones = () => axios.get(API_URL);
export const getMisionById = (id) => axios.get(`${API_URL}/${id}`);
export const createMision = (mision) => axios.post(API_URL, mision);
export const updateMision = (mision) => axios.patch(API_URL, mision);

export const connectNinjaToMision = async (misionId, ninjaId) => {

    const ninja = await ninjasApi.getNinjaById(ninjaId)
    return axios.patch(`${API_URL}/${misionId}`,ninja.data);
}

export const deleteMision = (id) => axios.delete(`${API_URL}/${id}`);
export const loadDefaultMisiones = () => axios.get(`${API_URL}/predeterminados`);
export const exportMision = (misionId, option) => axios.get(`${API_URL}/${misionId}/${option}`, { responseType: 'blob' });