import axios from 'axios';
import * as jutsusApi from '../bff/jutsus';
import * as aldeasApi from '../bff/aldeas';

const API_URL = 'http://localhost:8060/api/ninjas';

export const getAllNinjas = () => axios.get(API_URL);
export const getNinjaById = (id) => axios.get(`${API_URL}/${id}`);
export const createNinja = (ninja) => axios.post(API_URL, ninja);
export const updateNinja = (ninja) => axios.patch(API_URL, ninja);

export const connectJutsuToNinja = async (ninjaId, jutsuId) => {
    const jutsu = await jutsusApi.getJutsuById(jutsuId);
    return axios.patch(`${API_URL}/conectarnj/${ninjaId}`,jutsu.data);
}
export const connectAldeaToNinja = async (ninjaId, aldeaId) => {
    const aldea = await aldeasApi.getAldeaById(aldeaId);
    return axios.patch(`${API_URL}/conectarna/${ninjaId}`,aldea.data);
}
export const deleteNinja = (id) => axios.delete(`${API_URL}/${id}`);
export const exportNinja = (ninjaId, option) => axios.get(`${API_URL}/${ninjaId}/${option}`, { responseType: 'blob' });